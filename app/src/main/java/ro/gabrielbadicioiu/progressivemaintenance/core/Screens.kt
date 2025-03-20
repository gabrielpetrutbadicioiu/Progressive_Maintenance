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
    @Serializable
    data class EditProdLineScreen(val prodLineID:String):Screens()
    @Serializable
    data object RegisterCompanyMailScreen:Screens()
    @Serializable
    data class CompanyDetailsScreen(
        val selectedCountry:String,
        val userID:String?,
        val userEmail:String?
        ):Screens()
    @Serializable
    data class SelectCountryScreen(val currentUserId:String, val currentUserEmail:String):Screens()
    @Serializable
    data object CreateOwnerEmailScreen:Screens()
    @Serializable
    data class CreateOwnerPassScreen(val email:String, val poppedBackStack:Boolean):Screens()
    @Serializable
    data class OwnerAccDetailsScreen(
        val companyDocumentID:String,
        val userID:String?,
        val userEmail:String?
        ):Screens()
    @Serializable
    data object SelectCompanyScreen:Screens()
    @Serializable
    data object JoinSelectCompanyScreen:Screens()
    @Serializable
    data class JoinCompanyUserPassword(val companyID:String, val email:String, val hasPoppedBackStack:Boolean)
    @Serializable
    data class JoinCompanyCreateUserProfile(val companyID:String, val userID:String, val email:String)
}