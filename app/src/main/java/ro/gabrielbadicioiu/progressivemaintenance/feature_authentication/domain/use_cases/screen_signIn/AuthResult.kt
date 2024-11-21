package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn

import java.lang.Error

data class AuthResult(
    val isError:Boolean=false,
    var errorMessage:String=""
)
