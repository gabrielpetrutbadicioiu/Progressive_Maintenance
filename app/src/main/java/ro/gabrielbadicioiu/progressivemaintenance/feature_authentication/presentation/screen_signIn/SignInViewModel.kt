package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {

    //states
    var emailLabel by mutableStateOf("Email")
        private set
    var passLabel by mutableStateOf("Password")
        private set
    var icon: ImageVector by mutableStateOf(Icons.Filled.VisibilityOff)
        private set
    var email by mutableStateOf("")
        private set
    var pass by mutableStateOf("")
        private set
    var showPasswordChecked by mutableStateOf(false)
        private set
    var rememberMeChecked by mutableStateOf(false)
        private set
    var signInBtnEn by mutableStateOf(false)
        private set
    private var enteredPassword by mutableStateOf(false)
    private var enteredEmail by mutableStateOf(false)

    //one time events
    private val _eventFlow= MutableSharedFlow<UiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    sealed class UiEvent{
data class showToast(val message:String): UiEvent()
object SignUp: UiEvent()
    }

    fun onEvent(event: SignInScreenEvent)
    {

        when(event)
        {
            is SignInScreenEvent.EnteredEmail->
            {
                email=event.value
                enteredEmail = email.isNotBlank()
                signInBtnEn= enteredEmail&&enteredPassword
            }

            is SignInScreenEvent.EnteredPassword->{
                pass=event.value
                enteredPassword= pass.isNotBlank()
                signInBtnEn= enteredEmail&&enteredPassword
            }

            is SignInScreenEvent.OnShowPasswordClick->
            {
                showPasswordChecked=!showPasswordChecked
                if (showPasswordChecked)
                {
                    icon=Icons.Filled.Visibility
                }
                else{
                    icon=Icons.Filled.VisibilityOff
                }
            }

            is SignInScreenEvent.OnRememberMeCheck->{
                rememberMeChecked=!rememberMeChecked
                //TODO
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