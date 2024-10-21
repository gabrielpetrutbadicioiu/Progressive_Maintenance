package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.viewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email.EmailValidationUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email.ValidationResult



class EmailValidationViewModel(
    private val useCases: EmailValidationUseCases
):ViewModel() {

    //states
    var enteredEmail by mutableStateOf("")
        private set
    
    var validationResult by mutableStateOf(ValidationResult(isError = false, labelIcon = null))
        private set
//one time events
    sealed class EmailValidUiEvent()
{
        object OnBackBtnClick:EmailValidUiEvent()
    object OnContinueBtnClick:EmailValidUiEvent()
    }
    private val _eventFlow= MutableSharedFlow<EmailValidUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    fun onEvent(event: EmailValidationEvent)
    {
        when(event)
        {
            is EmailValidationEvent.EnteredEmail->{
                enteredEmail=event.value

            }
            is EmailValidationEvent.OnNextClick->{
               validationResult= useCases.validateEmail.execute(enteredEmail)

                viewModelScope.launch {
                    if (!validationResult.isError)
                    {
                        _eventFlow.emit(EmailValidUiEvent.OnContinueBtnClick)
                    }
                }

            }
            is EmailValidationEvent.OnBackBtnClick->{
                viewModelScope.launch {
                    _eventFlow.emit(EmailValidUiEvent.OnBackBtnClick)
                }
            }
            is EmailValidationEvent.OnContinueBtnClick->{
                viewModelScope.launch {
                    _eventFlow.emit(EmailValidUiEvent.OnContinueBtnClick)
                }
            }
        }
    }



}