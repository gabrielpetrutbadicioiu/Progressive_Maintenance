package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseCollections
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login.LoginScreenUseCases

class LoginViewModel(
    private val useCases: LoginScreenUseCases
):ViewModel() {
    private var firebaseUser:FirebaseUser?=null
    //states
    private val _selectedCompany= mutableStateOf(Company())

    private val _countDownTimer= mutableIntStateOf(90)
    val countDownTimer:State<Int> = _countDownTimer

    private val _enableResendBtn= mutableStateOf(false)
    val enableResendBtn:State<Boolean> = _enableResendBtn

    private val _showResendBtn= mutableStateOf(false)
    val showResendBtn:State<Boolean> = _showResendBtn

    private val _registeredCompanies= mutableStateOf<List<Company>>(emptyList())
    val registeredCompanies:State<List<Company>> = _registeredCompanies

    private val _companyID= mutableStateOf("")
    val companyID:State<String> = _companyID

    private val _userID= mutableStateOf("")
    val userID:State<String> = _userID

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

    private val _showDialog = mutableStateOf(false)
    val showDialog:State<Boolean> = _showDialog

    private val _companyQuery = mutableStateOf("")
    val companyQuery:State<String> = _companyQuery

    private val _filteredCompanies= mutableStateOf<List<Company>> (emptyList())
    val filteredCompanies:State<List<Company>> = _filteredCompanies

    private val _unverifiedEmailErr= mutableStateOf(false)
    val unverifiedEmailErr:State<Boolean> = _unverifiedEmailErr

//One time events
    private val _eventFlow= MutableSharedFlow<LoginScreenUiEvent> ()
    val eventFlow= _eventFlow.asSharedFlow()
    sealed class LoginScreenUiEvent{
        data class OnNavigateTo(val screen:Screens):LoginScreenUiEvent()
        data class OnShowToast(val message:String):LoginScreenUiEvent()
        data object OnOwnerEmailScreen:LoginScreenUiEvent()
        data object OnNavigateToHomeScreen:LoginScreenUiEvent()
        data object OnNavigateToJoinCompanyScreen:LoginScreenUiEvent()
        data object OnCountDown:LoginScreenUiEvent()
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
                viewModelScope.launch {useCases.rememberUser.execute(email = _user.value.email,
                    pass = _user.value.password,
                    isRemembered = _user.value.rememberMe,
                    employer = _selectedCompany.value.organisationName,
                    employerId = _selectedCompany.value.id)
                }
            }
            is LoginScreenEvent.GetRememberedUser->{
               val a= viewModelScope.launch {  _user.value=useCases.getRememberedUser.execute() }
                if (a.isCompleted)
                { _selectedCompany.value = _registeredCompanies.value.find { it.id == _user.value.companyID } ?: Company(organisationName = "pula")}
            }
            is LoginScreenEvent.OnSignInClick->{
                viewModelScope.launch {
                    useCases.onSignIn.execute(
                        email = _user.value.email,
                        pass = _user.value.password,
                        onSuccess = {
                            currentUser->
                            _unverifiedEmailErr.value=false
                            _showResendBtn.value=false
                            _errorMessage.value=""
                            _isError.value=false
                            if (_user.value.companyName.isEmpty())
                            {
                                _isError.value=true
                                _errorMessage.value="You must select the company you work for before logging in."
                            }
                            else{
                                viewModelScope.launch {
                                    try{
                                        useCases.checkUserInCompany.execute(
                                            companyID = _user.value.companyID,
                                            userID = currentUser?.uid.toString(),
                                            onFailure = {e->
                                                _isError.value=true
                                                _errorMessage.value=e
                                                _clickableErr.value=false
                                            },
                                            onSuccess = {user->
                                                _isError.value=false
                                                _errorMessage.value=""
                                                viewModelScope.launch { _eventFlow.emit(LoginScreenUiEvent.OnNavigateToHomeScreen) }
                                            },
                                            onUserNotFound = {
                                                _isError.value=true
                                                _errorMessage.value="Access denied: You are not registered with this company."
                                                _clickableErr.value=false
                                            }
                                        )
                                    } catch (e:Exception)
                                    {
                                        _isError.value=true
                                        _errorMessage.value=e.message.toString()
                                        viewModelScope.launch { _eventFlow.emit(LoginScreenUiEvent.OnShowToast(e.message.toString())) }
                                    }
                            }
                            }
                       },
                        onError = {e->
                            _isError.value=true
                            _errorMessage.value=e
                            _clickableErr.value=false
                        },
                        onUnverifiedEmailErr = {user->
                            _unverifiedEmailErr.value=true
                            _clickableErr.value=true
                            _errorMessage.value="Your email is not verified. Click here to send a verification email."
                            firebaseUser=user
                                               },
                    )
                }
            }
            is LoginScreenEvent.OnSendVerificationEmail->{
                viewModelScope.launch {
                        useCases.sendVerificationEmail.execute(
                            currentUser =firebaseUser ,
                            onSuccess = {message->
                                _errorMessage.value=message
                                _clickableErr.value=false
                                _showResendBtn.value=true
                                _countDownTimer.intValue=90
                                viewModelScope.launch { _eventFlow.emit(LoginScreenUiEvent.OnCountDown) }
                                        },
                            onFailure = {e->
                                _errorMessage.value=e}
                        )
                }
            }
            is LoginScreenEvent.OnRegisterCompanyClick->{
                viewModelScope.launch { _eventFlow.emit(LoginScreenUiEvent.OnOwnerEmailScreen) }
            }

            is LoginScreenEvent.OnSelectCompanyClick->{
                _showDialog.value=true
            }
            is LoginScreenEvent.OnFetchRegisteredCompanies->{
                viewModelScope.launch {
                    useCases.fetchRegisteredCompanies.execute(
                        collectionReference = Firebase.firestore.collection(FirebaseCollections.COMPANIES),
                        onSuccess = {companies-> _registeredCompanies.value=companies},
                        onFailure = {e-> viewModelScope.launch { _eventFlow.emit(LoginScreenUiEvent.OnShowToast(e)) }}
                    )
                }
            }
            is LoginScreenEvent.OnCancelDialogClick->{
                onCancelDialog()
            }
            is LoginScreenEvent.OnCompanySearch->{
                _companyQuery.value=event.query.replaceFirstChar { char-> char.uppercase() }
                _filteredCompanies.value=useCases.onCompanySearch.execute(searchedCompany = _companyQuery.value, companyList = _registeredCompanies.value)
            }
            is LoginScreenEvent.OnCompanyClick->{
                _selectedCompany.value=event.selectedCompany
                _user.value=_user.value.copy(companyID = _selectedCompany.value.id, companyName = _selectedCompany.value.organisationName)
                onCancelDialog()
            }
            is LoginScreenEvent.OnRememberUser->{
                viewModelScope.launch {useCases.rememberUser.execute(email = _user.value.email,
                    pass = _user.value.password,
                    isRemembered = _user.value.rememberMe,
                    employer = _selectedCompany.value.organisationName,
                    employerId = _selectedCompany.value.id)
                }
            }
            is LoginScreenEvent.OnJoinCompanyClick->{
                viewModelScope.launch { _eventFlow.emit(LoginScreenUiEvent.OnNavigateToJoinCompanyScreen) }
            }
            is LoginScreenEvent.OnCountDown->{

                    _countDownTimer.intValue--
                 _enableResendBtn.value=_countDownTimer.intValue==0
                   viewModelScope.launch { _eventFlow.emit(LoginScreenUiEvent.OnCountDown) }

            }
        }
    }
   private fun onCancelDialog()
    {
     _showDialog.value=false
        _companyQuery.value=""
    }
}