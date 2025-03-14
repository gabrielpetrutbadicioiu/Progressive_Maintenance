package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_join_selectCompany

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.FetchRegisteredCompanies
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.OnCompanySearch

data class JoinSelectCompanyUseCases(
    val fetchRegisteredCompanies: FetchRegisteredCompanies,
    val onCompanySearch: OnCompanySearch
)
