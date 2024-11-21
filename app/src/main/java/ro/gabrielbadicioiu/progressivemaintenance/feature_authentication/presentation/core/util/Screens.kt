package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.util

import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    data object SignInScreen:Screens()
    @Serializable
    data object EmailValidationScreen:Screens()
    @Serializable
    data class OTPScreen(val email:String):Screens()
    @Serializable
    data object CreatePassScreen:Screens()
    @Serializable
    data object UserNameScreen:Screens()
}