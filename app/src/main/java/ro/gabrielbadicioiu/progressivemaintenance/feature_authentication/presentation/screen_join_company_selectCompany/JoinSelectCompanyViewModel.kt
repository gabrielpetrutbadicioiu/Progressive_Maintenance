package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_join_company_selectCompany

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseCollections
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_join_selectCompany.JoinSelectCompanyUseCases

class JoinSelectCompanyViewModel(
    private val useCases: JoinSelectCompanyUseCases
):ViewModel (){
    //states
    private val _registrationEmail = mutableStateOf("")
    val registrationEmail:State<String> = _registrationEmail

    private val _emailMatchesPattern= mutableStateOf(false)
    val emailMatchesPattern:State<Boolean> =_emailMatchesPattern

    private val _showDialog= mutableStateOf(false)
    val showDialog:State<Boolean> = _showDialog

    private val _isError= mutableStateOf(false)
    val isError:State<Boolean> = _isError

    private val _errorMessage= mutableStateOf("")
    val errorMessage:State<String> = _errorMessage

    private val _registeredCompanies= mutableStateOf<List<Company>>(emptyList())
    val registeredCompanies:State<List<Company>> = _registeredCompanies

    private val _companyQuery= mutableStateOf("")
    val companyQuery:State<String> = _companyQuery

    private val _filteredCompanies= mutableStateOf<List<Company>>(emptyList())
    val filteredCompanies:State<List<Company>> = _filteredCompanies

    private val _selectedCompany= mutableStateOf(Company())
    val selectedCompany:State<Company> = _selectedCompany

    private val _enteredOTP = mutableStateOf("")
    val enteredOTP:State<String> = _enteredOTP


    //one time events
    private val _eventFlow= MutableSharedFlow<JoinSelectCompanyUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class JoinSelectCompanyUiEvent{
        data object OnContinueClick:JoinSelectCompanyUiEvent()
        data object OnNavigateUp:JoinSelectCompanyUiEvent()
    }

    fun onEvent(event:JoinSelectCompanyEvent)
    {
        when(event)
        {
            is JoinSelectCompanyEvent.OnRegistrationEmailChange->{
                _registrationEmail.value=event.newValue
                _emailMatchesPattern.value=Patterns.EMAIL_ADDRESS.matcher(_registrationEmail.value).matches()
            }
            is JoinSelectCompanyEvent.OnShowDialogClick->{
                _showDialog.value=true
            }
            is JoinSelectCompanyEvent.OnFetchRegisteredCompanies->{
                viewModelScope.launch {
                    useCases.fetchRegisteredCompanies.execute(
                        collectionReference = Firebase.firestore.collection(FirebaseCollections.COMPANIES),
                        onSuccess = { companies->
                            _registeredCompanies.value=companies
                                    _isError.value=false},
                        onFailure = {e-> _isError.value=true
                        _errorMessage.value=e}
                    )
                }
            }

            is JoinSelectCompanyEvent.OnCompanySearch->{
                _companyQuery.value=event.searchedValue.replaceFirstChar { char-> char.uppercase() }
                _filteredCompanies.value=useCases.onCompanySearch.execute(searchedCompany = _companyQuery.value, companyList = _registeredCompanies.value)
            }
            is JoinSelectCompanyEvent.OnDialogDismiss->{
                _showDialog.value=false
            }

            is JoinSelectCompanyEvent.OnCompanyClick->{
                _selectedCompany.value=event.selectedCompany.copy()
                _showDialog.value=false
            }

            is JoinSelectCompanyEvent.OnOtpValueChange->{
                _enteredOTP.value=event.enteredOTP
            }
            is JoinSelectCompanyEvent.OnContinueBtnClick->{
                if (_selectedCompany.value.otp==_enteredOTP.value)
                {
                    _isError.value=false
                   viewModelScope.launch {  _eventFlow.emit(JoinSelectCompanyUiEvent.OnContinueClick) }
                }
                else{
                    _isError.value=true
                    _errorMessage.value="The OTP you entered is incorrect. Please ensure you received the code from your company Owner."
                }
            }
            is JoinSelectCompanyEvent.OnNavigateUp->{
                viewModelScope.launch { _eventFlow.emit(JoinSelectCompanyUiEvent.OnNavigateUp) }
            }
        }
    }
}