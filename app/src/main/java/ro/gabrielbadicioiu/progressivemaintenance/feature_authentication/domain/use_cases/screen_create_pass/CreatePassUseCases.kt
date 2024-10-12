package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass

data class CreatePassUseCases (
val validateCreatedPass: ValidateCreatedPass,
    val showPassword: ShowPassword,
    val doPasswordsMatch: DoPasswordsMatch
)