package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
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
    //one time events
    private val _eventFlow= MutableSharedFlow<UiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    sealed class UiEvent{
data class ShowToast(val message:String): UiEvent()
data object SignUp: UiEvent()
    }

    fun onEvent(event: SignInScreenEvent)
    {

        when(event)
        {
            is SignInScreenEvent.EnteredEmail->
            {
                emailInput=event.value
            }

            is SignInScreenEvent.EnteredPassword->{
                passInput=event.value
            }

            is SignInScreenEvent.OnShowPasswordClick->
            {

              showPassResult= useCases.showPassword.execute(showPassResult)
            }

            is SignInScreenEvent.OnRememberMeCheck->{
                rememberMeChecked=!rememberMeChecked
                //TODO
            }

            is SignInScreenEvent.OnSignInBtnClick->{
                viewModelScope.launch {
                    authResult=
                        useCases.signIn.execute(email = emailInput, password = passInput)

                }
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