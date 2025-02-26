package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_companySelection

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
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class CompanySelectionViewModel(
    private val repository: CompaniesRepository
):ViewModel() {
    private val collectionReference=Firebase.firestore.collection(FirebaseCollections.COMPANIES)
    //states
    private val _selectedCompany= mutableStateOf("")
    val selectedCompany:State<String> = _selectedCompany

    private val _companies = mutableStateOf<List<Company>>(emptyList())
    val companies:State<List<Company>> = _companies

    private val _errMsg= mutableStateOf("")
    val errMsg:State<String> = _errMsg

    //one time events
    private val _eventFlow= MutableSharedFlow<CompanySelectionUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()
    sealed class CompanySelectionUiEvent{
        data object OnNavigateUp:CompanySelectionUiEvent()
    }

    fun onEvent(event: CompanySelectionScreenEvent)
    {
        when(event)
        {
            is CompanySelectionScreenEvent.FetchCompanies->{
                viewModelScope.launch {
                    repository.getAllCompanies(
                        collectionReference = collectionReference,
                        onSuccess = {companiesList->
                            _companies.value=companiesList
                        },
                        onFailure = {e->
                            _errMsg.value=e
                        }
                    )
                }
            }
            is CompanySelectionScreenEvent.OnCancelBtnClick->{
                _selectedCompany.value=""
                viewModelScope.launch { _eventFlow.emit(CompanySelectionUiEvent.OnNavigateUp) }
            }
        }
    }
}