package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails

import android.net.Uri
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
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_companyDetails.CompanyDetailsUseCases

class CompanyDetailsViewModel(
private val useCases: CompanyDetailsUseCases
):ViewModel() {
    private val companyCollectionReference=Firebase.firestore.collection(FirebaseCollections.COMPANIES)
    //states
    private val _companyDetails= mutableStateOf(Company())
    val companyDetails:State<Company> = _companyDetails

    private val _selectedImageUri = mutableStateOf<Uri?>(null)
    val selectedImageUri:State<Uri?> = _selectedImageUri

    private val _isError= mutableStateOf(false)
    val isError:State<Boolean> = _isError

    private val _errorMessage= mutableStateOf("")
    val errorMessage:State<String> = _errorMessage

    private val _currentUserID= mutableStateOf("")
    val currentUserID:State<String> = _currentUserID

    private val _currentUserEmail= mutableStateOf("")
    val currentUserEmail:State<String> = _currentUserEmail
//one time events
    private val _eventFlow= MutableSharedFlow<CompanyDetailsUiEvent> ()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class CompanyDetailsUiEvent
    {
        data object OnCountrySelectClick:CompanyDetailsUiEvent()
        data object OnNavigateUp:CompanyDetailsUiEvent()
        data class OnContinueToOwnerDetails(val documentID:String):CompanyDetailsUiEvent()
    }
    fun onEvent(event: CompanyDetailsScreenEvent)
    {
        when(event)
        {
            is CompanyDetailsScreenEvent.GetUserEmailAndID->{
                _currentUserID.value=event.currentUserID
                _currentUserEmail.value=event.currentUserEmail
            }
            is CompanyDetailsScreenEvent.OnOrganisationNameChange->{
                _companyDetails.value=_companyDetails.value.copy(organisationName = event.organisationName.replaceFirstChar { char-> char.uppercase() })
            }
            is CompanyDetailsScreenEvent.OnIndustryNameChange->{
                _companyDetails.value=_companyDetails.value.copy(industryType = event.industryName.replaceFirstChar { char-> char.uppercase() })
            }
            is CompanyDetailsScreenEvent.OnCountryNameChange->{
                _companyDetails.value= _companyDetails.value.copy(country = event.countryName.replaceFirstChar { char-> char.uppercase() })
            }
            is CompanyDetailsScreenEvent.OnSelectCountryClick->{
                viewModelScope.launch { _eventFlow.emit(CompanyDetailsUiEvent.OnCountrySelectClick) }
            }
            is CompanyDetailsScreenEvent.OnCountryInit->{
                if (event.country.isNotEmpty())
                {
                    _companyDetails.value= _companyDetails.value.copy(country = event.country.replaceFirstChar { char-> char.uppercase() })
                }
            }
            is CompanyDetailsScreenEvent.OnNavigateUp->{
                viewModelScope.launch { _eventFlow.emit(CompanyDetailsUiEvent.OnNavigateUp) }
            }
            is CompanyDetailsScreenEvent.OnContinueClick->{
                viewModelScope.launch {
                    useCases.onRegisterCompany.execute(
                        company = _companyDetails.value,
                        collectionReference = companyCollectionReference,
                        onSuccess = {documentID->
                            viewModelScope.launch { _eventFlow.emit(CompanyDetailsUiEvent.OnContinueToOwnerDetails(documentID)) }
                        },
                        onFailure = {e->
                            _isError.value=true
                            _errorMessage.value=e
                        }
                    )
                }
            }
            is CompanyDetailsScreenEvent.OnUriResult->{
                _companyDetails.value=_companyDetails.value.copy(companyLogoUrl = event.uri.toString())
                _selectedImageUri.value=event.uri
            }
        }
    }
}