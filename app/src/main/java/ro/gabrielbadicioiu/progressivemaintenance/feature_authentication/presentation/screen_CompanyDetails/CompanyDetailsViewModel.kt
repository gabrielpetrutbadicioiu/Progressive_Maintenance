package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company

class CompanyDetailsViewModel:ViewModel() {
    //states
    private val _companyDetails= mutableStateOf(Company())
    val companyDetails:State<Company> = _companyDetails


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
            is CompanyDetailsScreenEvent.OnCityNameChange->{
                _companyDetails.value=_companyDetails.value.copy(city = event.cityName.replaceFirstChar { char-> char.uppercase() })
            }
        }
    }
}