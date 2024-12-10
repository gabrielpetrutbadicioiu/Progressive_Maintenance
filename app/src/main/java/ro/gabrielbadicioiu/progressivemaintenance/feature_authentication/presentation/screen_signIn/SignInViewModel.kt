package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.ShowPassResult
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.AuthResult
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.SignInUseCases

class SignInViewModel(
    private val useCases: SignInUseCases
): ViewModel() {

    //states

var showPassResult by mutableStateOf(ShowPassResult())
        private set
    var emailInput by mutableStateOf("")
        private set
    var passInput by mutableStateOf("")
        private set

     var rememberMeChecked by mutableStateOf(false)
        private set
    var authResult by mutableStateOf(AuthResult())
        private set
    var rememberedUser by mutableStateOf(User())
        private set
    //one time events
    private val _eventFlow= MutableSharedFlow<UiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    sealed class UiEvent{
data class ShowToast(val message:String): UiEvent()
data object SignUp: UiEvent()
    }
init {
    viewModelScope.launch {
        rememberedUser=useCases.getRememberedUser.execute()
        rememberMeChecked=rememberedUser.rememberMe
        if(rememberMeChecked)
        {
            emailInput=rememberedUser.email
            passInput=rememberedUser.password
        }
    }

}
    fun onEvent(event: SignInScreenEvent)
    {

        when(event)
        {
            is SignInScreenEvent.EnteredEmail->
            {
                emailInput=event.value
                if (rememberMeChecked)
                {
                    viewModelScope.launch {
                        useCases.rememberMe.execute(email = emailInput, pass = passInput, isRememberMeActive = rememberMeChecked)
                    }

                }
            }

            is SignInScreenEvent.EnteredPassword->{
                passInput=event.value
                if (rememberMeChecked){
                    viewModelScope.launch {
                        useCases.rememberMe.execute(email = emailInput, pass = passInput, isRememberMeActive = rememberMeChecked)
                    }
                }
            }

            is SignInScreenEvent.OnShowPasswordClick->
            {
              showPassResult= useCases.showPassword.execute(showPassResult)
            }

            is SignInScreenEvent.OnRememberMeCheck->{

                rememberMeChecked=!rememberMeChecked
                viewModelScope.launch {
                    rememberedUser=useCases.rememberMe.execute(
                        email = emailInput,
                        pass = passInput,
                        isRememberMeActive = rememberMeChecked)
                }

            }

            is SignInScreenEvent.OnSignInBtnClick->{

                viewModelScope.launch {
                        useCases.signIn.execute(
                            email = emailInput,
                            password = passInput,
                            onSuccess = {
                                isEmailVerified->
                                authResult=authResult.copy(
                                    isError = false,
                                    errorMessage = "",
                                    isEmailVerified = isEmailVerified
                                    )
                            },
                            onError = {
                                error->
                                authResult=authResult.copy(
                                    isError = true,
                                    errorMessage = error,
                                    isEmailVerified = null
                                )
                            })
                }
//


            }

            is SignInScreenEvent.OnCreateAccClick->
            {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.SignUp)
                }

            }
        }//when
    }//onEvent

}