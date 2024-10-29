package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_userName

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_userName.UserNameUseCases

class UserNameViewModel(
    private val useCases: UserNameUseCases
):ViewModel()
{
    //one time events
    sealed class UserNameUiEvent{
        data object OnBackBtnClick:UserNameUiEvent()
        data object OnFinishBtnClick:UserNameUiEvent()
    }
    private val _eventFlow= MutableSharedFlow<UserNameUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    //states
    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var firstNameErr by mutableStateOf(false)
        private set
    var lastNameErr by mutableStateOf(false)
        private set
    var firstNameLabel by mutableStateOf("First name")
        private set
    var lastNameLabel by mutableStateOf("Last name")
        private set
    fun onEvent(event: UserNameEvent)
    {
        when(event)
        {
            is UserNameEvent.FirstNameInput->{
                val nameResult= useCases.nameValidation.execute(event.value)
                firstName=nameResult.capitalizedName
                firstNameErr=nameResult.isError
                firstNameLabel =
                    if(firstNameErr) "The name can only contain letters. Numbers or special characters are not allowed." else "First name"
            }
            is UserNameEvent.LastNameInput->{
                val nameResult= useCases.nameValidation.execute(event.value)
                lastName=nameResult.capitalizedName
                lastNameErr=nameResult.isError
                lastNameLabel =
                    if(lastNameErr) "The name can only contain letters. Numbers or special characters are not allowed." else "Last name"


            }
            is UserNameEvent.OnBackBtnClick->{
                viewModelScope.launch {
                    _eventFlow.emit(UserNameUiEvent.OnBackBtnClick)
                }
            }
            is UserNameEvent.OnFinishBtnClick->{
               viewModelScope.launch {
                   _eventFlow.emit(UserNameUiEvent.OnFinishBtnClick)
               }
            }
        }
    }
}