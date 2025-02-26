package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login.LoginScreenUseCases

class LoginViewModel(
    private val useCases: LoginScreenUseCases
):ViewModel() {


    private var firebaseUser:FirebaseUser?=null
    //states
    private val _company= mutableStateOf("")
    val company:State<String> = _company

    private val _isError= mutableStateOf(false)
    val isError:State<Boolean> = _isError

    private val _showPassword= mutableStateOf(false)
    val showPassword:State<Boolean> = _showPassword

    private val _user= mutableStateOf(User())
    val user:State<User> = _user

    private val _errorMessage= mutableStateOf("")
    val errorMessage:State<String> = _errorMessage

    private val _clickableErr= mutableStateOf(false)
    val clickableErr:State<Boolean> = _clickableErr

//One time events
    private val _eventFlow= MutableSharedFlow<LoginScreenUiEvent> ()
    val eventFlow= _eventFlow.asSharedFlow()
    sealed class LoginScreenUiEvent{
        data class OnNavigateTo(val screen:Screens):LoginScreenUiEvent()
        data object OnOwnerEmailScreen:LoginScreenUiEvent()
    }
    fun onEvent(event: LoginScreenEvent)
    {
        when(event)
        {
            is LoginScreenEvent.OnEmailChange->{
                _user.value=_user.value.copy(email = event.email)
            }
            is LoginScreenEvent.OnPassChange->{
                _user.value=_user.value.copy(password = event.pass)
            }
            is LoginScreenEvent.OnShowPassClick->{
                _showPassword.value=!_showPassword.value
            }
            is LoginScreenEvent.OnCheckedChange->{
                _user.value=_user.value.copy(rememberMe = !_user.value.rememberMe)
                viewModelScope.launch {useCases.rememberUser.execute(email = _user.value.email, pass = _user.value.password, isRemembered = _user.value.rememberMe)
                }
            }
            is LoginScreenEvent.GetRememberedUser->{
                viewModelScope.launch {  _user.value=useCases.getRememberedUser.execute() }
            }
            is LoginScreenEvent.OnSignInClick->{
                viewModelScope.launch {
                    useCases.onSignInClick.execute(
                        email = _user.value.email,
                        pass = _user.value.password,
                        company = _company.value,
                        onSuccess = {isEmailVerified, currentUser->
                            if (isEmailVerified!=null && currentUser!=null)
                            {
                                if (isEmailVerified){
                                    viewModelScope.launch { _eventFlow.emit(LoginScreenUiEvent.OnNavigateTo(Screens.HomeScreen)) }
                                }
                                else{
                                    _clickableErr.value=true
                                    _isError.value=true
                                    _errorMessage.value="Your email is not verified. Click here to send a verification link to your email."
                                    firebaseUser=currentUser
                                }
                            }
                        },
                        onError = {e->
                            _isError.value=true
                            _errorMessage.value=e
                        }
                    )
                }
            }
            is LoginScreenEvent.OnSendVerificationEmail->{
                viewModelScope.launch {
                        useCases.sendVerificationEmail.execute(
                            currentUser =firebaseUser ,
                            onSuccess = {_errorMessage.value=it},
                            onFailure = {_errorMessage.value=it}
                        )
                }
            }
            is LoginScreenEvent.OnRegisterCompanyClick->{
                viewModelScope.launch { _eventFlow.emit(LoginScreenUiEvent.OnOwnerEmailScreen) }
            }

            is LoginScreenEvent.OnSelectCompanyClick->{
                viewModelScope.launch { _eventFlow.emit(LoginScreenUiEvent.OnNavigateTo(Screens.SelectCompanyScreen)) }
            }
        }
    }
}