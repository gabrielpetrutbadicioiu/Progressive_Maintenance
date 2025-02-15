package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_OwnerAccDetailsScreen

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OwnerAccDetails.OwnerAccDetailsUseCases

class OwnerAccDetailsViewModel(
    val useCases: OwnerAccDetailsUseCases
):ViewModel() {

    //states
    private val _firstName = mutableStateOf("")
    val firstName:State<String> = _firstName

    private val _firstNameErr = mutableStateOf(false)
    val firstNameErr:State<Boolean> = _firstNameErr

    private val _firstNameSupportingText= mutableStateOf("This name will be visible to other users*")
    val firstNameSupportingText:State<String> = _firstNameSupportingText

    private val _lastName= mutableStateOf("")
    val lastName:State<String> = _lastName

    private val _lastNameSupportingText= mutableStateOf("This name will be visible to other users*")
    val lastNameSupportingText:State<String> = _lastNameSupportingText

    private val _lastNameErr = mutableStateOf(false)
    val lastNameErr:State<Boolean> = _lastNameErr

    private val _position= mutableStateOf("")
    val position:State<String> = _position

    private val _selectedImageUri= mutableStateOf<Uri?>(null)
    val selectedImageUri:State<Uri?> = _selectedImageUri



    //one time events
    private val _eventFlow=MutableSharedFlow<OwnerAccDetailsUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class OwnerAccDetailsUiEvent{
        data object OnNavigateUp:OwnerAccDetailsUiEvent()
    }

    fun onEvent(event: OwnerAccDetailsScreenEvent)
    {
        when(event)
        {
            is OwnerAccDetailsScreenEvent.OnFirstNameChange->{
                _firstName.value=event.firstName.trim().replaceFirstChar {char-> char.uppercase() }
            }

            is OwnerAccDetailsScreenEvent.OnLastNameChange->{
                _lastName.value=event.lastname.trim().replaceFirstChar { char-> char.uppercase() }
            }
            is OwnerAccDetailsScreenEvent.OnNavigateUp->{
                viewModelScope.launch { _eventFlow.emit(OwnerAccDetailsUiEvent.OnNavigateUp)}
            }
            is OwnerAccDetailsScreenEvent.OnPositionChange->{
                _position.value=event.position.trim().replaceFirstChar { char-> char.uppercase() }
            }
            is OwnerAccDetailsScreenEvent.OnUriResult->{
                _selectedImageUri.value=event.uri
            }

        }
    }

}