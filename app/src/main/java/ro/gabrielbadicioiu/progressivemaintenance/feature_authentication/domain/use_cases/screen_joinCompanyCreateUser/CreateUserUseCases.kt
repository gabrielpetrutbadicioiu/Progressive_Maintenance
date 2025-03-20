package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_joinCompanyCreateUser

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.OnAddUserToCompany

data class CreateUserUseCases(
    val onAddUserToCompany: OnAddUserToCompany
)
