package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.ShowPassword

data class CreatePassUseCases (
    val validateCreatedPass: ValidateCreatedPass,
    val showPassword: ShowPassword,
    val doPasswordsMatch: DoPasswordsMatch
)