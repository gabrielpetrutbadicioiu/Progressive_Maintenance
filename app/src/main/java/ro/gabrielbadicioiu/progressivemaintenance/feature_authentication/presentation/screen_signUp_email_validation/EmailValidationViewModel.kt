package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email.EmailValidationUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.core.ValidationResult

class EmailValidationViewModel(
    val useCases: EmailValidationUseCases
):ViewModel() {

    //states
    var enteredEmail by mutableStateOf("")
        private set

    var nextBtnEn by mutableStateOf(false)
        private set
    var validationResult by mutableStateOf(ValidationResult(successful = false, errorMessage = null))
        private set
    var label by mutableStateOf("Email")
        private set
    var isError by mutableStateOf(false)
        private set
    var emailLabelIcon:ImageVector? by mutableStateOf(null)
        private set


    fun onEvent(event: EmailValidationEvent)
    {
        when(event)
        {
            is EmailValidationEvent.EnteredEmail->{
                enteredEmail=event.value
                nextBtnEn = enteredEmail.isNotBlank()
            }
            is EmailValidationEvent.OnNextClick->{
               validationResult= useCases.validateEmail.execute(enteredEmail)
                if (validationResult.successful)
                {
                    label="Email"
                    emailLabelIcon=null
                    isError=false
                }
                else{
                    isError=true
                    label=validationResult.errorMessage.toString()
                    emailLabelIcon=Icons.Default.WarningAmber
                }
            }
        }
    }



}