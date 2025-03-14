package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_OwnerAccDetailsScreen

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
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OwnerAccDetails.OwnerAccDetailsUseCases

class OwnerAccDetailsViewModel(
    val useCases: OwnerAccDetailsUseCases
):ViewModel() {
    private val storageRef=Firebase.storage.reference

    //states
    private val _user = mutableStateOf(UserDetails(
        rank = UserRank.OWNER.name
    ))
    val user:State<UserDetails> = _user

    private val _lastNameErr = mutableStateOf(false)
    val lastNameErr:State<Boolean> = _lastNameErr

    private val _firstNameErr= mutableStateOf(false)
    val firstNameErr:State<Boolean> = _firstNameErr

    private val _registerErr= mutableStateOf(false)
    val registerErr:State<Boolean> = _registerErr

    private val _errMsg= mutableStateOf("")
    val errMsg:State<String> = _errMsg

    private val _selectedImageUri = mutableStateOf<Uri?>(null)
    val selectedImageUri:State<Uri?> = _selectedImageUri
    //one time events
    private val _eventFlow=MutableSharedFlow<OwnerAccDetailsUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class OwnerAccDetailsUiEvent{
        data object OnNavigateUp:OwnerAccDetailsUiEvent()
        data object OnNavigateToSignInScreen:OwnerAccDetailsUiEvent()
    }

    fun onEvent(event: OwnerAccDetailsScreenEvent)
    {
        when(event)
        {
            is OwnerAccDetailsScreenEvent.OnFirstNameChange->{

                _user.value=_user.value.copy(
                    firstName = event.firstName.replaceFirstChar {char-> char.uppercase() }
                )
            }

            is OwnerAccDetailsScreenEvent.OnLastNameChange->{
                _user.value=_user.value.copy(lastName =event.lastname.replaceFirstChar { char-> char.uppercase() } )
            }
            is OwnerAccDetailsScreenEvent.OnNavigateUp->{
                viewModelScope.launch { _eventFlow.emit(OwnerAccDetailsUiEvent.OnNavigateUp)}
            }
            is OwnerAccDetailsScreenEvent.OnPositionChange->{
                _user.value=_user.value.copy(position = event.position.replaceFirstChar { char-> char.uppercase() })
            }
            is OwnerAccDetailsScreenEvent.OnUriResult->{
                _selectedImageUri.value=event.uri
                _user.value=_user.value.copy(profilePicture = event.uri.toString())
            }
            is OwnerAccDetailsScreenEvent.GetUserEmailAndID->{
                _user.value=_user.value.copy(email = event.currentUserEmail, userID = event.currentUserID)
            }
            is OwnerAccDetailsScreenEvent.OnFinishBtnClick->{
                viewModelScope.launch {
                    useCases.onAddUserToCompany.execute(
                        user = _user.value.copy(),
                        onFirstNameFail = {
                            _firstNameErr.value=true
                            _errMsg.value="Invalid first name: Only letters and spaces are allowed."
                        },
                        onLastNameFail = {
                            _lastNameErr.value=true
                            _errMsg.value="Invalid last name: Only letters and spaces are allowed."
                        },
                        companyID = event.companyID,
                        onSuccess = {
                            _registerErr.value=false
                            _lastNameErr.value=false
                            _firstNameErr.value=false
                            _errMsg.value=""
                           viewModelScope.launch {_eventFlow.emit(OwnerAccDetailsUiEvent.OnNavigateToSignInScreen)  }
                        },
                        onFailure = {e->
                            _registerErr.value=true
                            _errMsg.value=e
                        },
                        imageReference = storageRef,
                        imageName = _user.value.userID,
                        localUri = _selectedImageUri.value,
                        imageFolderName = CloudStorageFolders.USER_PROFILE_PICTURES
                    )
                }

            }

        }
    }

}