package ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases.MembersScreenUseCases

class MembersScreenViewModel(
    private val useCases: MembersScreenUseCases,

):ViewModel() {

    //states
    private val _companyID= mutableStateOf("")
    val companyID:State<String> = _companyID

    private val _userID= mutableStateOf("")
    val userID:State<String> = _userID

    private val _userDetails= mutableStateOf(UserDetails())
    val userDetails:State<UserDetails> = _userDetails


    private val _userList= mutableStateOf<List<UserDetails>>(emptyList())
    val userList:State<List<UserDetails>> = _userList

    private val _memberStatus= mutableStateOf<List<MemberStatus>>(emptyList())
    val memberStatus:State<List<MemberStatus>> = _memberStatus

    private val _isError= mutableStateOf(false)
    val isError:State<Boolean> = _isError

    private val _errMsg= mutableStateOf("")
    val errMsg:State<String> = _errMsg

    private val _tappedUser= mutableStateOf(MemberStatus())
    val tappedUser:State<MemberStatus> = _tappedUser

    private val _showEditPositionAlertDialog= mutableStateOf(false)
    val showEditPositionAlertDialog:State<Boolean> = _showEditPositionAlertDialog

    private val _showBanAlertDialog= mutableStateOf(false)
    val showBanAlertDialog:State<Boolean> = _showBanAlertDialog

    private val _showBannedUsers= mutableStateOf(false)
    val showBannedUsers:State<Boolean> = _showBannedUsers

    private val _isBanned= mutableStateOf(false)
    val isBanned:State<Boolean> = _isBanned

    private val _bannedList=mutableStateOf<List<UserDetails>>(emptyList())
    val bannedList:State<List<UserDetails>> = _bannedList

    //one time events
    private val _eventFlow= MutableSharedFlow<MembersScreenUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    sealed class MembersScreenUiEvent{
        data object OnAddProductionLineClick:MembersScreenUiEvent()
        data object OnHomeBtnClick:MembersScreenUiEvent()
        data object OnProfileClick:MembersScreenUiEvent()
        data class ToastMessage(val message:String):MembersScreenUiEvent()

    }

    fun onEvent(event: MembersScreenEvent)
    {
        when(event)
        {
            is MembersScreenEvent.OnActionBtnClick->{
                if (_userDetails.value.rank!=UserRank.USER.name)
                {
                    viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.OnAddProductionLineClick) }
                }
            }
            is MembersScreenEvent.OnHomeBtnClick->{
                viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.OnHomeBtnClick) }
            }
            is MembersScreenEvent.OnFetchArgumentData->{
                _userID.value=event.userId
                _companyID.value=event.companyId
                viewModelScope.launch {
                    try {
                        useCases.getUserInCompany.execute(
                            userId = _userID.value,
                            companyId = _companyID.value,
                            onSuccess = {currentUserDetails->
                                _userDetails.value=currentUserDetails.copy()
                            },
                            onFailure = {e->
                                viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage(e)) }
                            }
                        )
                    }catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage(e.message?:"Failed to fetch current user details")) }
                    }

                }
            }
            is MembersScreenEvent.OnFetchAllUsersInCompany->{
                viewModelScope.launch {
                    try {
                        useCases.getAllUsersInCompany.execute(
                            companyId = _companyID.value,
                            onResult = { userList->
                                _userList.value=userList
                               _memberStatus.value= userList.map { user->
                                   MemberStatus(user = user)
                               }
                                _bannedList.value=userList.filter { user-> user.hasBeenBanned }
                                       },
                            onFailure = {e->
                                _isError.value=true
                                _errMsg.value=e
                            }
                        )
                    }catch (e:Exception)
                    {
                        _isError.value=true
                        _errMsg.value=e.message?:"Failed to fetch users"
                    }
                }
            }
            is MembersScreenEvent.OnShowDropdownMenu->{
                _tappedUser.value=event.user.copy()
                _memberStatus.value=_memberStatus.value.mapIndexed { _, memberStatus ->
                    if (memberStatus.user.userID==event.user.user.userID)
                    {
                        memberStatus.copy(showDropDown = true)
                    }
                    else{
                        memberStatus.copy(showDropDown = false)
                    }
                }
            }
            is MembersScreenEvent.OnDismissDropdownMenu->{
                _memberStatus.value=_memberStatus.value.map { member->
                    member.copy(showDropDown = false)
                }
            }
            is MembersScreenEvent.OnChangeUserRank->{
                try {
                    viewModelScope.launch {
                        useCases.onChangeUserRank.execute(
                            promoteTo = event.rank,
                            user = event.user.user,
                            companyId = _companyID.value,
                            onFailure = {e->
                                viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage(e)) }
                            },
                            onSuccess = {
                                viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage("${event.user.user.firstName} ${event.user.user.lastName} rank updated to ${event.rank}")) }
                                onEvent(MembersScreenEvent.OnDismissDropdownMenu)
                            }
                        )
                    }
                }catch (e:Exception)
                {
                    viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage("Failed to promote User to Admin")) }
                }
            }
            is MembersScreenEvent.OnEditPositionClick->{
                _tappedUser.value=event.user.copy()
                _showEditPositionAlertDialog.value=true
                _memberStatus.value=_memberStatus.value.map { member->
                    member.copy(showDropDown = false)
                }

            }
            is MembersScreenEvent.OnPositionValueChange->{
                _tappedUser.value=_tappedUser.value.copy(user = _tappedUser.value.user.copy(position = event.newPosition.replaceFirstChar { char-> char.uppercase() }))
            }
            is MembersScreenEvent.OnDismissEditPositionAlertDialog->{
                _showEditPositionAlertDialog.value=false
            }
            is MembersScreenEvent.OnUpdatePositionConfirm->{
                try {
                    viewModelScope.launch {
                        useCases.onUpdateUserPosition.execute(
                            companyId = _companyID.value,
                            user = _tappedUser.value.copy(),
                            onSuccess = {
                                viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage(
                                    "${ _tappedUser.value.user.firstName} ${_tappedUser.value.user.lastName} position has been updated to ${_tappedUser.value.user.position}"))
                                }
                                _tappedUser.value=MemberStatus()
                                _showEditPositionAlertDialog.value=false
                            },
                            onFailure = {e->
                                viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage(e)) }
                            }
                        )
                    }
                }catch (e:Exception)
                {
                    viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage(e.message?:"Couldn't update user's position")) }
                }
            }
            is MembersScreenEvent.OnBanClick->{
                _tappedUser.value=event.tappedUser.copy()
                _showBanAlertDialog.value=true
                _memberStatus.value=_memberStatus.value.map { member->
                    member.copy(showDropDown = false)
                }
                _isBanned.value=true
                _memberStatus.value=_memberStatus.value.map { member->
                    member.copy(showDropDown = false)
                }
            }
            is MembersScreenEvent.OnDismissBanDialog->{
                _tappedUser.value=MemberStatus()
                _showBanAlertDialog.value=false
            }
            is MembersScreenEvent.OnBanConfirm->{

                try {
                    viewModelScope.launch {
                        useCases.onBanConfirm.execute(
                            companyId = _companyID.value,
                            user = _tappedUser.value.copy(),
                            isBanned = true,
                            onFailure = {e->  viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage(e)) }},
                            onSuccess = {
                                _showBanAlertDialog.value=false
                                viewModelScope.launch {
                                    _eventFlow.emit(MembersScreenUiEvent.ToastMessage(
                                        "${_tappedUser.value.user.firstName} ${_tappedUser.value.user.lastName} has been banned!"
                                    ))
                                }}
                        )
                    }
                }catch (e:Exception)
                {
                    viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage(e.message?:"Failed to ban user")) }
                }
            }
            is MembersScreenEvent.OnUnbanConfirm->{
                _showBanAlertDialog.value=false
                try {
                    viewModelScope.launch {
                        useCases.onBanConfirm.execute(
                            companyId = _companyID.value,
                            user = _tappedUser.value,
                            isBanned = false,
                            onFailure = {e->  viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage(e)) }},
                            onSuccess = {
                                viewModelScope.launch {
                                    _eventFlow.emit(MembersScreenUiEvent.ToastMessage(
                                        "${_tappedUser.value.user.firstName} ${_tappedUser.value.user.lastName} has been unbanned!"
                                    ))
                                }}
                        )
                    }
                }catch (e:Exception)
                {
                    viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.ToastMessage(e.message?:"Failed to unban user")) }
                }
            }
            is MembersScreenEvent.OnShowBannedUserToggle->{
                _showBannedUsers.value=!_showBannedUsers.value
            }
            is MembersScreenEvent.OnUnbanClick->{
                _showBanAlertDialog.value=true
                _isBanned.value=false
                _memberStatus.value=_memberStatus.value.map { member->
                    member.copy(showDropDown = false)}
            }
            is MembersScreenEvent.OnNavigateToProfile->{
                viewModelScope.launch { _eventFlow.emit(MembersScreenUiEvent.OnProfileClick)}
            }
        }
    }
}