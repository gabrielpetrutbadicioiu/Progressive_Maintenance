package ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.GetUserInCompany

data class ProfileScreenUseCases(
    val getUserInCompany: GetUserInCompany
)
