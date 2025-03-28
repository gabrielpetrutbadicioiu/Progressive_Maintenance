package ro.gabrielbadicioiu.progressivemaintenance.core

import kotlinx.serialization.Serializable


sealed class Screens {
    @Serializable
    data object SignInScreen: Screens()
    @Serializable
    data class AddProductionLineScreen(val companyID: String, val userID: String):Screens()
    @Serializable
    data class HomeScreen(val companyID:String, val userID:String):Screens()
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