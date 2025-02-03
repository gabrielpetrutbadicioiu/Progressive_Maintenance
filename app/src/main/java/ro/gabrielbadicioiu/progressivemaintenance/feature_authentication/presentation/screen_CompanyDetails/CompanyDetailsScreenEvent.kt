package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails

sealed class CompanyDetailsScreenEvent {
    data class OnOrganisationNameChange(val organisationName:String):CompanyDetailsScreenEvent()
    data class OnIndustryNameChange(val industryName:String):CompanyDetailsScreenEvent()
    data class OnCountryNameChange(val countryName:String):CompanyDetailsScreenEvent()
    data class OnCityNameChange(val cityName:String):CompanyDetailsScreenEvent()
}