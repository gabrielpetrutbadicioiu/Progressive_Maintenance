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
    data class CompanyDetailsScreen(val selectedCountry:String):Screens()
    @Serializable
    data object SelectCountryScreen:Screens()
    @Serializable
    data class CreateOwnerEmailScreen(val organisationName:String, val country:String, val industry:String, val companyLogo:String)
    @Serializable
    data class CreateOwnerPassScreen(val email:String, val organisationName:String, val country:String, val industry:String, val companyLogo:String)
    @Serializable
    data class OwnerAccDetailsScreen(val email:String, val password:String, val organisationName:String, val country:String, val industry:String, val companyLogo:String)
}