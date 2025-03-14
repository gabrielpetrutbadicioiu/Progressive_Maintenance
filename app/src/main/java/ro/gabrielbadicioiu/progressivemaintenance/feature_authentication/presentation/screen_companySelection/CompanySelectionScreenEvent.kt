package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_companySelection

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company

sealed class CompanySelectionScreenEvent {

    data object FetchCompanies:CompanySelectionScreenEvent()
    data object OnCancelBtnClick:CompanySelectionScreenEvent()
    data class OnCompanyClick(val company:Company):CompanySelectionScreenEvent()
    data class OnSearchedCompany(val value:String):CompanySelectionScreenEvent()
}