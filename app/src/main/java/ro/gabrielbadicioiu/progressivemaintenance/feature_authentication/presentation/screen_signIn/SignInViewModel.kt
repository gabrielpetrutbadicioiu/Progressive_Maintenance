package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.ShowPassResult
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.AuthResult
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.SignInUseCases

class SignInViewModel(
    private val useCases: SignInUseCases
): ViewModel() {

    //states
    var verifyEmailTxtColor by mutableStateOf(Color.Red)
        private set
    var verifyEmailIcon by mutableStateOf(Icons.Default.WarningAmber)
        private set
    var verifyEmailTxt by mutableStateOf("Your email is not verified. Click here to send a verification link to your email.")
        private set
var currentUser:FirebaseUser? by mutableStateOf(null)
            private set
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
    private val _eventFlow= MutableSharedFlow<SignInScreenUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    sealed class SignInScreenUiEvent{
data class ShowToast(val message:String): SignInScreenUiEvent()
data object SignUp: SignInScreenUiEvent()
        data object SignIn:SignInScreenUiEvent()
        data class NavigateTo(val screen:Screens):SignInScreenUiEvent()
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
                        useCases.rememberMe.execute(
                            email = emailInput,
                            pass = passInput,
                            isRememberMeActive = rememberMeChecked)
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
                                isEmailVerified, user->
                                authResult=authResult.copy(
                                    isError = false,
                                    errorMessage = "",
                                    isEmailVerified = isEmailVerified
                                    )
                                currentUser=user
                                if (currentUser?.isEmailVerified==true)
                                {
                                    viewModelScope.launch {
                                        _eventFlow.emit(SignInScreenUiEvent.SignIn)
                                    }
                                }


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
                    _eventFlow.emit(SignInScreenUiEvent.SignUp)
                }

            }
            is SignInScreenEvent.OnSendVerificationEmail->{
                useCases.sendVerificationEmail.execute(
                    currentUser = currentUser,
                    onSuccess = {
                        verifyEmailTxt="An verification email has been sent. Check your email address"
                        verifyEmailIcon=Icons.Default.WarningAmber
                        verifyEmailTxtColor= Color.Red
                    },
                    onFailure = {
                        error->
                        verifyEmailTxt="Your email is not verified. Click here to send a verification email"
                        verifyEmailIcon=Icons.Default.WarningAmber
                        verifyEmailTxtColor= Color.Red
                        viewModelScope.launch {
                            _eventFlow.emit(SignInScreenUiEvent.ShowToast(error.toString()))
                        }
                    }

                )
            }
            is SignInScreenEvent.OnRegisterCompanyClick->{
                viewModelScope.launch {
                    _eventFlow.emit(SignInScreenUiEvent.NavigateTo(Screens.CompanyDetailsScreen))
                }
            }
        }//when
    }//onEvent

}