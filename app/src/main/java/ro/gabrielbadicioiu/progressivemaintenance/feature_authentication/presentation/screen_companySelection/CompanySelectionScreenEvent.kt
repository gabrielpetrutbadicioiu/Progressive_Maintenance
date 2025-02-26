package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_companySelection

sealed class CompanySelectionScreenEvent {

    data object FetchCompanies:CompanySelectionScreenEvent()
    data object OnCancelBtnClick:CompanySelectionScreenEvent()
}