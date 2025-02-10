package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company

class CompanyDetailsViewModel:ViewModel() {
    //states
    private val _companyDetails= mutableStateOf(Company())
    val companyDetails:State<Company> = _companyDetails

//one time events
    private val _eventFlow= MutableSharedFlow<CompanyDetailsUiEvent> ()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class CompanyDetailsUiEvent()
    {
        data object OnCountrySelectClick:CompanyDetailsUiEvent()
        data object OnNavigateUp:CompanyDetailsUiEvent()
    }
    fun onEvent(event: CompanyDetailsScreenEvent)
    {
        when(event)
        {
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
        }
    }
}