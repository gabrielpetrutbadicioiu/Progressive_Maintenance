package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails

import android.net.Uri

sealed class CompanyDetailsScreenEvent {
    data class OnOrganisationNameChange(val organisationName:String):CompanyDetailsScreenEvent()
    data class OnIndustryNameChange(val industryName:String):CompanyDetailsScreenEvent()
    data class OnCountryNameChange(val countryName:String):CompanyDetailsScreenEvent()
    data class OnCountryInit(val country:String):CompanyDetailsScreenEvent()
    data class OnUriResult(val uri: Uri?):CompanyDetailsScreenEvent()

    data object OnSelectCountryClick:CompanyDetailsScreenEvent()
    data object OnNavigateUp:CompanyDetailsScreenEvent()
    data object OnContinueClick:CompanyDetailsScreenEvent()

}