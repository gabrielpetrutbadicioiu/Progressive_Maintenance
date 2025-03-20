package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OwnerAccDetails

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.OnAddUserToCompany

data class OwnerAccDetailsUseCases(
    val isValidName: IsValidName,
    val onAddUserToCompany: OnAddUserToCompany
)
