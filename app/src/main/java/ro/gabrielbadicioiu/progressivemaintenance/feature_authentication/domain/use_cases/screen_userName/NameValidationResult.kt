package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_userName

data class NameValidationResult(
    val isError:Boolean,
    val capitalizedName:String
)
