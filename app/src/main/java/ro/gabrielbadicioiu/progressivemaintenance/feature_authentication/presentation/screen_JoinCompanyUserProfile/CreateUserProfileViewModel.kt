package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserProfile

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.core.CloudStorageFolders
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_joinCompanyCreateUser.CreateUserUseCases
import kotlin.random.Random

class CreateUserProfileViewModel(
    private val useCases: CreateUserUseCases,
    private val companiesRepository: CompaniesRepository
):ViewModel()
{ private val storageRef= Firebase.storage.reference
    //states
    private val _user = mutableStateOf(UserDetails(rank = UserRank.USER.name))
    val user: State<UserDetails> = _user

    private val _showProgressBar= mutableStateOf(false)
    val showProgressBar:State<Boolean> = _showProgressBar

    private val _company= mutableStateOf(Company())
    val company:State<Company> =_company

    private val _firstNameErr= mutableStateOf(false)
    val firstNameErr:State<Boolean> = _firstNameErr

    private val _lastNameErr = mutableStateOf(false)
    val lastNameErr:State<Boolean> = _lastNameErr
    private val _registerErr= mutableStateOf(false)
    val registerErr:State<Boolean> = _registerErr

    private val _errMsg= mutableStateOf("")
    val errMsg:State<String> = _errMsg
    private val _selectedImageUri = mutableStateOf<Uri?>(null)

    private var args=CreateUserProfileArgumentData()
    //one time events
    private val _eventFlow = MutableSharedFlow<CreateUserProfileUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()


    sealed class CreateUserProfileUiEvent{
        data class OnNavigateBack(val hasPoppedBackStack:Boolean):CreateUserProfileUiEvent()
        data class OnShowToast(val message:String):CreateUserProfileUiEvent()
        data object OnNavigateToSignIn:CreateUserProfileUiEvent()

    }

    fun onEvent(event:CreateUserProfileScreenEvent)
    {
        when(event)
        {
            is CreateUserProfileScreenEvent.OnNavigateUp->{
                viewModelScope.launch { _eventFlow.emit(CreateUserProfileUiEvent.OnNavigateBack(true)) }
            }
            is CreateUserProfileScreenEvent.OnUriResult->{
                _selectedImageUri.value=event.uri

                val uri=if (event.uri!=null)event.uri.toString() else ""
                _user.value=_user.value.copy(profilePicture = uri)
            }
            is CreateUserProfileScreenEvent.OnFirstNameChange->{
                _user.value=_user.value.copy(firstName = event.name.replaceFirstChar { char-> char.uppercase() })
            }
            is CreateUserProfileScreenEvent.OnLastNameChange->{
                _user.value=_user.value.copy(lastName = event.name.replaceFirstChar { char-> char.uppercase() })
            }
            is CreateUserProfileScreenEvent.OnPositionChange->{
                _user.value=_user.value.copy(position = event.position.replaceFirstChar { char-> char.uppercase() })
            }
            is CreateUserProfileScreenEvent.OnFinishBtnClick->{
                _showProgressBar.value=true
                viewModelScope.launch {
                    useCases.onAddUserToCompany.execute(
                        user = _user.value.copy(),
                        onLastNameFail = {
                            _showProgressBar.value=false
                            _lastNameErr.value=true
                            _errMsg.value="Invalid last name: Only letters and spaces are allowed."
                        },
                        onFirstNameFail = {
                            _showProgressBar.value=false
                            _firstNameErr.value=true
                            _errMsg.value="Invalid first name: Only letters and spaces are allowed."
                        },
                        companyID = event.companyID,
                        localUri = _selectedImageUri.value,
                        imageFolderName = CloudStorageFolders.USER_PROFILE_PICTURES,
                        onFailure = {e->
                            _showProgressBar.value=false
                            _registerErr.value=true
                            _errMsg.value=e
                        },
                        onSuccess = {

                            _registerErr.value=false
                            _lastNameErr.value=false
                            _firstNameErr.value=false
                            _errMsg.value=""
                            _showProgressBar.value=false
                            onEvent(CreateUserProfileScreenEvent.OnUpdateCompanyOTP)


                        },
                        imageReference = storageRef,
                        imageName = _user.value.userID
                    )
                }
            }
            is CreateUserProfileScreenEvent.OnGetCurrentUser->{
                _user.value=_user.value.copy(email = event.email, userID = event.userID)
            }
            is CreateUserProfileScreenEvent.OnUpdateCompanyOTP->{
                val otp= Random.nextInt(100000, 999999).toString()
                _company.value=_company.value.copy(otp = otp)
                viewModelScope.launch {
                    try {
                        companiesRepository.updateCompany(
                            companyId = args.companyID,
                            updatedCompany = _company.value.copy(),
                            onSuccess = { viewModelScope.launch {_eventFlow.emit(CreateUserProfileUiEvent.OnNavigateToSignIn)  }},
                            onFailure = {e-> viewModelScope.launch { _eventFlow.emit(CreateUserProfileUiEvent.OnShowToast(e)) }}
                        )
                    }catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(CreateUserProfileUiEvent.OnShowToast(e.message?:"ViewModel:Failed to update company otp")) }
                    }
                }
            }
            is CreateUserProfileScreenEvent.OnFetchArgumentData->{
                args=args.copy(companyID = event.companyID, userID = event.userID)
                viewModelScope.launch {
                    try {
                        companiesRepository.getCompanyById(
                            companyId = event.companyID,
                            onSuccess = {comp-> _company.value=comp},
                            onFailure = {e-> viewModelScope.launch { _eventFlow.emit(CreateUserProfileUiEvent.OnShowToast(e)) }}
                        )
                    }catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(CreateUserProfileUiEvent.OnShowToast(e.message?:"ViewModel:Failed to fetch company")) }
                    }
                }
            }
        }
    }
}