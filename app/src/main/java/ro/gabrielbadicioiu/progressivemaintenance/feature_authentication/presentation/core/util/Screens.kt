package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.util

import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
     object SignInScreen:Screens()
    @Serializable
    object EmailValidationScreen:Screens()
    @Serializable
    object OTPScreen:Screens()
}