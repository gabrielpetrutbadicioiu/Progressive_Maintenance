package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.core

data class ValidationResult(
    val successful:Boolean,
    val errorMessage:String?=null
)
