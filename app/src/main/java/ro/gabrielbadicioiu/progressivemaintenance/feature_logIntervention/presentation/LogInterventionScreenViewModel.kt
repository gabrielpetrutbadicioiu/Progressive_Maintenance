package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases.LogInterventionScreenUseCases

class LogInterventionScreenViewModel(
    private val companiesRepository: CompaniesRepository,
    private val useCases: LogInterventionScreenUseCases
) :ViewModel()
{
    //vars
    private var _companyId:String?=null
    private var _userId:String?=null
    //states
    private val _author= mutableStateOf(UserDetails())
    val author: State<UserDetails> = _author

    private val _fetchAuthorError= mutableStateOf(false)
    val fetchAuthorError:State<Boolean> = _fetchAuthorError

    private val _fetchAuthorErrMsg= mutableStateOf("")
    val fetchAuthorErrMsg:State<String> = _fetchAuthorErrMsg

    private val _selectedShift= mutableStateOf("")
    val selectedShift:State<String> = _selectedShift

    private val _isShiftDropDownExpanded = mutableStateOf(false)
    val isShiftDropDownMenuExpanded:State<Boolean> = _isShiftDropDownExpanded

    private val _employeeList= mutableStateOf<List<UserDetails>>(emptyList())
    val employeeList:State<List<UserDetails>> = _employeeList

    private val _participantsList= mutableStateOf<List<UserDetails>>(emptyList())
    val participantsList:State<List<UserDetails>> = _participantsList

    private val _selectedParticipant= mutableStateOf(UserDetails())
   // val selectedParticipant:State<UserDetails> = _selectedParticipant

    private val _fetchEmployeesErr= mutableStateOf(false)
    val fetchEmployeesErr:State<Boolean> = _fetchEmployeesErr

    private val _fetchEmployeesErrMsg= mutableStateOf("")
    val fetchEmployeesErrMsg:State<String> = _fetchEmployeesErrMsg

    private val _isOtherParticipantsDropdownExpanded= mutableStateOf(false)
    val isOtherParticipantsDropdownMenuExpanded:State<Boolean> = _isOtherParticipantsDropdownExpanded


    //one time events
    sealed class LogInterventionUiEvent{
        data class ShowToast(val message:String):LogInterventionUiEvent()
        data object OnNavigateToHome:LogInterventionUiEvent()
    }
    private val _eventFlow= MutableSharedFlow<LogInterventionUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()




    fun onEvent(event:LogInterventionScreenEvent)
    {
        when(event)
        {
            is LogInterventionScreenEvent.GetArgumentData->{
                _companyId=event.companyId
                _userId=event.userId
                viewModelScope.launch {

                        try {
                            companiesRepository.getUserInCompany(
                                currentUserID = _userId!!,
                                companyID = _companyId!!,
                                onSuccess = {user->
                                    if (user != null) {
                                        _author.value=user.copy()
                                        _fetchAuthorError.value=false
                                        _fetchAuthorErrMsg.value=""
                                    }
                                    else{
                                        _fetchAuthorError.value=true
                                        _fetchAuthorErrMsg.value="Viewmodel: User is null"
                                    }
                                },
                                onFailure = {e->
                                    _fetchAuthorError.value=true
                                    _fetchAuthorErrMsg.value=e
                                },
                                onUserNotFound = {
                                    _fetchAuthorError.value=true
                                    _fetchAuthorErrMsg.value="ViewModel: User not found"
                                }
                            )
                        }catch (e:Exception)
                        {
                            _fetchAuthorError.value=true
                            _fetchAuthorErrMsg.value=e.message?:"ViewModel: failed to fetch author"
                        }
                }
                //fetch all users
                viewModelScope.launch {
                    try {
                        companiesRepository.fetchAllUsersInCompany(
                            companyID = _companyId!!,
                            onResult = {employeeList->
                                _employeeList.value=employeeList
                                _fetchEmployeesErr.value=false
                            },
                            onFailure = {e->
                                _fetchEmployeesErr.value=true
                                _fetchEmployeesErrMsg.value=e
                            }
                        )
                    }catch (e:Exception)
                    {
                        _fetchEmployeesErr.value=true
                        _fetchEmployeesErrMsg.value=e.message?:"Viewmodel: Failed to fetch employees"
                    }
                }
            }//fetch data

            is LogInterventionScreenEvent.OnShiftClick->{
                _selectedShift.value=event.shift
                onEvent(LogInterventionScreenEvent.OnShiftDropdownDismiss)
            }
            is LogInterventionScreenEvent.OnExpandShiftDropDown->{
                _isShiftDropDownExpanded.value=true
            }
            is LogInterventionScreenEvent.OnShiftDropdownDismiss->{
                _isShiftDropDownExpanded.value=false
            }
            is LogInterventionScreenEvent.OnExpandOtherParticipantsDropdownMenu->{
                _isOtherParticipantsDropdownExpanded.value=true
            }
            is LogInterventionScreenEvent.OnOtherParticipantsDropdownMenuDismiss->{
                _isOtherParticipantsDropdownExpanded.value=false
            }
            is LogInterventionScreenEvent.OnParticipantClick->{
                _selectedParticipant.value=event.participant
                useCases.onSelectInterventionParticipant.execute(
                    selectedEmployee = _selectedParticipant.value,
                    participantList = _participantsList.value,
                    onFailure = {e->
                        viewModelScope.launch {_eventFlow.emit(LogInterventionUiEvent.ShowToast(e))}
                    },
                    onSuccess = {participants->
                        _participantsList.value=participants
                        onEvent(LogInterventionScreenEvent.OnOtherParticipantsDropdownMenuDismiss)
                    }
                )
            }
            is LogInterventionScreenEvent.OnRemoveParticipant-> {
                _participantsList.value -= event.participant
            }
            is LogInterventionScreenEvent.OnNavigateToHome->{
                viewModelScope.launch { _eventFlow.emit(LogInterventionUiEvent.OnNavigateToHome) }
            }

        }
    }



}