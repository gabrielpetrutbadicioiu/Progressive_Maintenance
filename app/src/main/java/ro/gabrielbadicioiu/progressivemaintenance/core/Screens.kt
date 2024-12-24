package ro.gabrielbadicioiu.progressivemaintenance.core

import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    data object SignInScreen: Screens()
    @Serializable
    data object EmailValidationScreen: Screens()
    @Serializable
    data class OTPScreen(val email:String): Screens()
    @Serializable
    data class CreatePassScreen(val validatedEmail:String): Screens()
    @Serializable
    data class UserNameScreen(val validatedEmail:String, val validatedPass:String): Screens()
    @Serializable
    data object AddEquipmentScreen:Screens()
    @Serializable
    data object HomeScreen:Screens()
}