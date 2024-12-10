package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_userName

data class UserNameUseCases(
    val nameValidation: NameValidation,
    val signUp: SignUp
)
