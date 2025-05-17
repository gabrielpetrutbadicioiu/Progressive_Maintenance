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
    data class EditProdLineScreen(val companyId:String, val userID: String, val productionLineId:String):Screens()
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
    data class JoinCompanyUserPassword(val companyID:String, val email:String, val hasPoppedBackStack:Boolean):Screens()
    @Serializable
    data class JoinCompanyCreateUserProfile(val companyID:String, val userID:String, val email:String):Screens()
    @Serializable
    data class MembersScreenRoute(val companyID: String, val userId:String):Screens()
    @Serializable
    data object BannedScreen:Screens()
    @Serializable
    data class ProfileScreenRoute(val companyId:String, val userId:String):Screens()
    @Serializable
    data class LogInterventionScreen(
        val companyId:String,
        val userId:String,
        val productionLineId: String,
        val equipmentId:String,
        val equipmentName:String,
        val readOnly:Boolean,
        val interventionId:String,
        val prodLineName:String):Screens()
    @Serializable
    data class DisplayInterventionsScreen(
        val displayAllInterventions:Boolean,
        val displayLineInterventions:Boolean,
        val displayEquipmentInterventions:Boolean,
        val companyId:String,
        val userId:String,
        val lineId:String,
        val equipmentId:String,
        ):Screens()
}