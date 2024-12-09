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
    data class CreatePassScreen(val validatedEmail:String):Screens()
    @Serializable
    data class UserNameScreen(val validatedEmail:String, val validatedPass:String):Screens()
}