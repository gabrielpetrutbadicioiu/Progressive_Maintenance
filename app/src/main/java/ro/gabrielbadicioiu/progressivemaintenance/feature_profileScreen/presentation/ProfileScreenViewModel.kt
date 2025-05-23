package ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.presentation

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.core.CloudStorageFolders
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CloudStorageRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.domain.use_cases.ProfileScreenUseCases

class ProfileScreenViewModel(
    private val useCases: ProfileScreenUseCases,
    private val companiesRepository: CompaniesRepository,
    private val cloudStorageRepository: CloudStorageRepository,
    private val accountService: AccountService
):ViewModel()
{
    //states
    private val _userId= mutableStateOf("")
    val userId:State<String> =_userId

    private val _companyId= mutableStateOf("")
    val companyId:State<String> = _companyId

    private val _loggedUser= mutableStateOf(UserDetails())
    val loggedUser:State<UserDetails> =_loggedUser

    private val _fetchUserErr= mutableStateOf(false)
    val fetchUserErr:State<Boolean> = _fetchUserErr

    private val _fetchUserErrMsg= mutableStateOf("")
    val fetchUserErrMsg:State<String> = _fetchUserErrMsg

    private val _fetchCompanyErr= mutableStateOf(false)
    val fetchCompanyErr:State<Boolean> = _fetchCompanyErr

    private val _fetchCompanyErrMsg= mutableStateOf("")
    val fetchCompanyErrMsg:State<String> = _fetchCompanyErrMsg

    private val _loggedCompany= mutableStateOf(Company())
    val loggedCompany:State<Company> = _loggedCompany

    private val _showOtp= mutableStateOf(false)
    val showOtp:State<Boolean> = _showOtp

    private val _showLoadProfilePhotoProgressBar= mutableStateOf(false)
    val showLoadProfilePhotoProgressBar:State<Boolean> = _showLoadProfilePhotoProgressBar

    private val _selectedTab= mutableIntStateOf(0)
    val selectedTab:State<Int> = _selectedTab

    private val _firstName= mutableStateOf("")
    val firstName:State<String> = _firstName

    private val _isFirstNameEditing= mutableStateOf(false)
    val isFirstNameEditing:State<Boolean> = _isFirstNameEditing

    private val _lastName= mutableStateOf("")
    val lastName:State<String> = _lastName

    private val _isLastNameEditing= mutableStateOf(false)
    val isLastNameEditing:State<Boolean> = _isLastNameEditing

    private val _isSheetOpen= mutableStateOf(false)
    val isSheetOpen:State<Boolean> = _isSheetOpen

    private val _showOldPass= mutableStateOf(false)
    val showOldPass:State<Boolean> = _showOldPass

    private val _showNewPass= mutableStateOf(false)
    val showNewPass:State<Boolean> = _showNewPass

    private val _showConfNewPass= mutableStateOf(false)
    val showConfNewPass:State<Boolean> = _showConfNewPass

    private val _oldPassInput= mutableStateOf("")
    val oldPassInput:State<String> = _oldPassInput

    private val _newPassInput= mutableStateOf("")
    val newPassInput:State<String> =_newPassInput

    private val _confNewPassInput= mutableStateOf("")
    val confNewPassInput:State<String> = _confNewPassInput

    private val _isPassChangeErr= mutableStateOf(false)
    val isPassChangeErr:State<Boolean> = _isPassChangeErr

    private val _passChangeErrMsg= mutableStateOf("")
    val passChangeErrMsg:State<String> = _passChangeErrMsg

    private val _isOrganisationNameEditing= mutableStateOf(false)
    val isOrganisationNameEditing:State<Boolean> = _isOrganisationNameEditing

    private val _editingOrganisationName= mutableStateOf("")
    val editingOrganisationName:State<String> = _editingOrganisationName

    private val _isOrganisationDomainEditing= mutableStateOf(false)
    val isOrganisationDomainEditing:State<Boolean> = _isOrganisationDomainEditing

    private val _editingOrganisationDomain= mutableStateOf("")
    val editingOrganisationDomain:State<String> = _editingOrganisationDomain

    //one time events
    private val _eventFlow= MutableSharedFlow<ProfileScreenUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()

    sealed class ProfileScreenUiEvent{
        data object OnNavigateHome:ProfileScreenUiEvent()
        data object OnNavigateToMembers:ProfileScreenUiEvent()
        data object OnNavigateToAddProductionLine:ProfileScreenUiEvent()
        data object OnNavigateToLoginScreen:ProfileScreenUiEvent()

        data class OnShowToast(val message:String):ProfileScreenUiEvent()
        data class OnAnimateToPage(val page:Int):ProfileScreenUiEvent()
    }

    fun onEvent(event: ProfileScreenEvent)
    {
        when(event)
        {

            is ProfileScreenEvent.OnNavigateHome->{
                viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnNavigateHome) }
            }

            is ProfileScreenEvent.OnNavigateToMembers->{
                viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnNavigateToMembers) }
            }

            is ProfileScreenEvent.OnAddProductionLineClick->{
                viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnNavigateToAddProductionLine) }
            }
            is ProfileScreenEvent.OnGetLoggedUser->{
                try {
                    viewModelScope.launch {
                        companiesRepository.getCompanyById(
                            companyId = _companyId.value,
                            onSuccess = {company ->
                                _fetchCompanyErr.value=false
                                _fetchCompanyErrMsg.value=""
                                _loggedCompany.value=company.copy()
                                _editingOrganisationName.value=_loggedCompany.value.organisationName
                                _editingOrganisationDomain.value=_loggedCompany.value.industryType
                            },
                            onFailure = {e->
                                _fetchCompanyErr.value=true
                                _fetchCompanyErrMsg.value=e
                            }
                        )
                    }
                }
                catch (e:Exception)
                {
                    _fetchCompanyErrMsg.value=e.message?:"Viewmodel:Failed to fetch company"
                }

                try {
                    viewModelScope.launch {
                        useCases.getUserInCompany.execute(
                            userId = _userId.value,
                            companyId = _companyId.value,
                            onSuccess = {currentUser->
                                _loggedUser.value=currentUser
                                _fetchUserErr.value=false
                                _loggedUser.value=currentUser.copy()
                                _firstName.value=_loggedUser.value.firstName
                                _lastName.value=_loggedUser.value.lastName
                            },
                            onFailure = {e->
                                _fetchUserErr.value=true
                                _fetchUserErrMsg.value=e
                            }
                        )
                    }
                }catch (e:Exception){
                    _fetchUserErr.value=true
                    _fetchUserErrMsg.value=e.message?:"Failed to fetch user!"
                }

            }
            is ProfileScreenEvent.OnGetArgumentData->{
                _userId.value=event.userId
                _companyId.value=event.companyId
            }

            is ProfileScreenEvent.OnShowOtpClick->{
                _showOtp.value=!_showOtp.value
            }
            is ProfileScreenEvent.OnProfileUriResult->{
                _showLoadProfilePhotoProgressBar.value=true
                viewModelScope.launch {
                    try {
                        cloudStorageRepository.putFile(
                            imageName =_loggedUser.value.userID,
                            folderName = CloudStorageFolders.USER_PROFILE_PICTURES,
                            localUri = event.uri,
                            imageReference = Firebase.storage.reference,
                            onFailure = {e->
                                viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast(e)) }
                                _showLoadProfilePhotoProgressBar.value=false
                            },
                            onSuccess = {downloadUrl->
                                _loggedUser.value=_loggedUser.value.copy(profilePicture = downloadUrl)
                                onEvent(ProfileScreenEvent.OnUpdateUser("avatar"))
                            }
                        )
                    }catch (e:Exception)
                    {
                        _showLoadProfilePhotoProgressBar.value=false
                        viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast(e.message?:"Viewmodel:Failed to load photo url")) }

                    }
                }

            }
            is ProfileScreenEvent.OnUpdateUser->{
                viewModelScope.launch {
                    try {
                        companiesRepository.updateUser(
                            companyId = _companyId.value,
                            user = _loggedUser.value,
                            onSuccess = {
                                _showLoadProfilePhotoProgressBar.value=false
                                when(event.update)
                                {
                                    "avatar"->
                                        viewModelScope.launch {_eventFlow.emit(ProfileScreenUiEvent.OnShowToast(message = "Your profile photo has been updated."))}
                                    "firstName"->{
                                        viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast("First name updated successfully.")) }
                                    }
                                    "lastName"->{
                                        viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast("Last name updated successfully.")) }

                                    }
                                }
                            },
                            onFailure = {e->
                                _showLoadProfilePhotoProgressBar.value=false
                                viewModelScope.launch {_eventFlow.emit(ProfileScreenUiEvent.OnShowToast(message = e))}
                            }
                        )
                    }catch (e:Exception)
                    {
                        _showLoadProfilePhotoProgressBar.value=false
                        viewModelScope.launch {_eventFlow.emit(ProfileScreenUiEvent.OnShowToast(message = e.message?:"Failed to change photo!"))}

                    }
                }
            }
            is ProfileScreenEvent.OnUserTabClick->{
                _selectedTab.intValue=0

            }
            is ProfileScreenEvent.OnCompanyTabClick->{
                _selectedTab.intValue=1
            }
            is ProfileScreenEvent.OnAnimateScrollToPage->{
                _selectedTab.intValue=event.page
                viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnAnimateToPage(event.page)) }
            }
            is ProfileScreenEvent.OnCompanyLogoUriResult->{
                _showLoadProfilePhotoProgressBar.value=true
                try {
                    viewModelScope.launch {
                        cloudStorageRepository.putFile(
                            imageReference = Firebase.storage.reference,
                            imageName = _loggedCompany.value.organisationName,
                            folderName = CloudStorageFolders.COMPANY_LOGOS,
                            localUri = event.uri,
                            onSuccess = {downloadUrl->
                                _loggedCompany.value=_loggedCompany.value.copy(companyLogoUrl = downloadUrl)
                                onEvent(ProfileScreenEvent.OnUpdateCompany("logo"))
                            },
                            onFailure = {e->
                                viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast(e)) }
                                _showLoadProfilePhotoProgressBar.value=false
                            }
                        )
                    }
                }catch (e:Exception)
                {
                    viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast(e.message?:"ViewModel:Failed to load company logo")) }
                    _showLoadProfilePhotoProgressBar.value=false
                }
            }
            is ProfileScreenEvent.OnUpdateCompany-> {
                try {
                    viewModelScope.launch {
                        companiesRepository.updateCompany(
                            companyId = _loggedCompany.value.id,
                            updatedCompany = _loggedCompany.value.copy(),
                            onFailure = {e->
                                _showLoadProfilePhotoProgressBar.value=false
                                viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast(e)) }
                            },
                            onSuccess = {
                                _showLoadProfilePhotoProgressBar.value=false
                                when(event.update)
                                {
                                    "logo"->{
                                        viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast("Company logo updated successfully.")) }
                                    }
                                    "organisationName"->{
                                        viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast("Organisation name updated successfully.")) }
                                    }
                                    "domain"->{
                                        viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast("Organisation industry updated successfully.")) }

                                    }

                                }
                            }
                        )
                    }
                }catch (e:Exception)
                {
                    viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast(e.message?:"ViewModel:Failed to update company")) }

                }
            }
            is ProfileScreenEvent.OnEditFirstNameClick->{
                _isFirstNameEditing.value=true
            }
            is ProfileScreenEvent.OnFirstNameChange->{
                _firstName.value=event.fistName.replaceFirstChar { char->char.uppercase() }
            }
            is ProfileScreenEvent.OnCancelFirstNameEditClick->{
                _isFirstNameEditing.value=false
                _firstName.value=_loggedUser.value.firstName
            }
            is ProfileScreenEvent.OnDoneEditFirstNameClick->{
                val regex =Regex("^[a-zA-Z\\-\\s]+$")
                if (_firstName.value.isNotBlank()&&regex.matches(_firstName.value))
                {
                    _loggedUser.value=_loggedUser.value.copy(firstName = _firstName.value)
                    onEvent(ProfileScreenEvent.OnUpdateUser("firstName"))
                    _isFirstNameEditing.value=false
                }
                else{

                    viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast("Name must contain only letters, and cannot be empty.")) }}
            }
            is  ProfileScreenEvent.OnEditLastNameClick->{
                _isLastNameEditing.value=true
            }
            is ProfileScreenEvent.OnEditLastNameCancelClick->{
                _isLastNameEditing.value=false
                _lastName.value=_loggedUser.value.lastName
            }
            is ProfileScreenEvent.OnLastNameChange->{
                _lastName.value=event.lastName.replaceFirstChar { char-> char.uppercase() }
            }
            is ProfileScreenEvent.OnDoneEditLastName->{
                val regex =Regex("^[a-zA-Z\\-\\s]+$")
                if (_lastName.value.isNotBlank()&&regex.matches(_lastName.value))
                {
                    _loggedUser.value=_loggedUser.value.copy(lastName = _lastName.value)
                    onEvent(ProfileScreenEvent.OnUpdateUser("lastName"))
                    _isLastNameEditing.value=false
                }
                else{

                    viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast("Name must contain only letters, and cannot be empty.")) }}
            }
            is ProfileScreenEvent.OnLogoutClick->{
                viewModelScope.launch { accountService.logOut() }
               viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnNavigateToLoginScreen) }
            }

            is ProfileScreenEvent.OnOpenSheet->{
                _isSheetOpen.value=true
            }
            is ProfileScreenEvent.OnCloseSheet->{
                _isSheetOpen.value=false
                _showOldPass.value=false
                _showNewPass.value=false
                _showConfNewPass.value=false
                _oldPassInput.value=""
                _newPassInput.value=""
                _confNewPassInput.value=""
            }

            is ProfileScreenEvent.OnShowOldPass->{
                _showOldPass.value=!_showOldPass.value
            }
            is ProfileScreenEvent.OnShowNewPass->{
                _showNewPass.value=!_showNewPass.value
            }
            is ProfileScreenEvent.OnShowConfNewPass->{
                _showConfNewPass.value=!_showConfNewPass.value
            }
            is ProfileScreenEvent.OnOldPassChange->{
                _oldPassInput.value=event.oldPass
            }
            is ProfileScreenEvent.OnNewPassChange->{
                _newPassInput.value=event.newPass
            }
            is ProfileScreenEvent.OnConfNewPassChange->{
                _confNewPassInput.value=event.confNewPass
            }
            is ProfileScreenEvent.OnConfirmPassChange->{
                viewModelScope.launch {
                    try {
                        accountService.reAuthenticate(
                            userId = _userId.value,
                            oldPass = _oldPassInput.value,
                            onSuccess = {
                                _isPassChangeErr.value=false
                                _passChangeErrMsg.value=""
                                onEvent(ProfileScreenEvent.OnUpdateUserPass)
                            },
                            onFailure = {e->
                                _isPassChangeErr.value=true
                                _passChangeErrMsg.value=e
                            }
                        )
                    }catch (e:Exception)
                    {
                        _isPassChangeErr.value=true
                        _passChangeErrMsg.value=e.message?:"ViewModel: authentication failed "
                    }
                }
            }
            is ProfileScreenEvent.OnUpdateUserPass->{
                viewModelScope.launch {
                    try {
                        accountService.updatePassword(
                            newPass = _newPassInput.value,
                            onSuccess = {
                                _isPassChangeErr.value=false
                                _passChangeErrMsg.value=""
                                _isSheetOpen.value=false
                                viewModelScope.launch {_eventFlow.emit(ProfileScreenUiEvent.OnShowToast("Password changed successfully."))}
                                        },
                            onFailure = {e->
                                _isPassChangeErr.value=true
                                _passChangeErrMsg.value=e

                            })
                    } catch (e:Exception)
                    {
                        _isPassChangeErr.value=true
                        _passChangeErrMsg.value=e.message?:"ViewModel: Failed to update pass"
                    }
                }
            }
            is  ProfileScreenEvent.OnOrganisationNameChange->{
                _editingOrganisationName.value=event.organisationName.replaceFirstChar { char-> char.uppercase() }
            }
            is ProfileScreenEvent.OnEditOrganisationNameClick->{
                _isOrganisationNameEditing.value=true
            }
            is ProfileScreenEvent.OnEditOrganisationNameCancel->{
                _isOrganisationNameEditing.value=false
                _editingOrganisationName.value=_loggedCompany.value.organisationName
            }
            is ProfileScreenEvent.OnEditingOrganisationNameConfirm->{
                if (_editingOrganisationName.value.isNotBlank())
                {
                    _loggedCompany.value=_loggedCompany.value.copy(organisationName = _editingOrganisationName.value)
                    _isOrganisationNameEditing.value=false
                    onEvent(ProfileScreenEvent.OnUpdateCompany("organisationName"))
                }
                else{
                    viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast("Organisation name cannot be empty")) }
                }
            }

            is  ProfileScreenEvent.OnEditOrganisationDomain->{
                _isOrganisationDomainEditing.value=true
            }
            is ProfileScreenEvent.OnEditOrganisationDomainCancel->{
                _isOrganisationDomainEditing.value=false
                _editingOrganisationDomain.value=_loggedCompany.value.industryType
            }
            is ProfileScreenEvent.OnOrganisationDomainChange->{
                _editingOrganisationDomain.value=event.organisationDomain.replaceFirstChar { char-> char.uppercase() }
            }
            is ProfileScreenEvent.OnOrganisationDomainEditConfirm-> {
                if (_editingOrganisationDomain.value.isNotEmpty())
                {
                    _loggedCompany.value=_loggedCompany.value.copy(industryType = _editingOrganisationDomain.value)
                    _isOrganisationDomainEditing.value=false
                    onEvent(ProfileScreenEvent.OnUpdateCompany("domain"))
                }
                else{
                    viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnShowToast("Organisation Industry cannot be empty")) }
                }
            }
            }
        }
    }
