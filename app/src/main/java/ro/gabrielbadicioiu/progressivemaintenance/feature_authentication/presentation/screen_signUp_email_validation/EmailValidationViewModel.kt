package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
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



    fun onEvent(event: EmailValidationEvent)
    {
        when(event)
        {
            is EmailValidationEvent.EnteredEmail->{
                enteredEmail=event.value

            }
            is EmailValidationEvent.OnNextClick->{
               validationResult= useCases.validateEmail.execute(enteredEmail)

            }
        }
    }



}