package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.CreatePassUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.CreatedPassValidationResult
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.PassMatchingResult
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.ShowPassResult

class CreatePassViewModel(
   private val  useCases: CreatePassUseCases
):ViewModel()
{
     private val minPassLength = 8
     private val maxPassLength = 20

    var inputPassword by mutableStateOf("")
        private set
    var confirmPassInput by mutableStateOf("")
        private set

    var createdPassValidationResult by mutableStateOf(CreatedPassValidationResult())
        private set
    var showPassResult by mutableStateOf(ShowPassResult())
        private set
    var passMatchingResult by mutableStateOf(PassMatchingResult())
        private set


    fun onEvent(event: CreatePassEvent)
    {
        when(event)
        {

            is CreatePassEvent.PassInput->{
                inputPassword=event.value
              createdPassValidationResult= useCases
                  .validateCreatedPass
                  .execute(
                  inputPass = inputPassword,
                  minPassLength = minPassLength,
                    maxPassLength = maxPassLength
                )
                passMatchingResult=useCases.doPasswordsMatch.execute(inputPassword, confirmPassInput,createdPassValidationResult.isPassValid )
            }
            is CreatePassEvent.OnShowPassClick->{
               showPassResult= useCases.showPassword.execute(showPassResult = showPassResult)
            }

            is CreatePassEvent.ConfirmPassInput->{
                confirmPassInput=event.value
                passMatchingResult=useCases.doPasswordsMatch.execute(inputPassword, confirmPassInput, createdPassValidationResult.isPassValid)
            }
        }
    }
}