package ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.presentation

import android.net.Uri

sealed class ProfileScreenEvent {
    data object OnNavigateHome:ProfileScreenEvent()
    data object OnNavigateToMembers:ProfileScreenEvent()
    data object OnGetLoggedUser:ProfileScreenEvent()
    data object OnAddProductionLineClick:ProfileScreenEvent()
    data object OnShowOtpClick:ProfileScreenEvent()
    data object OnUserTabClick:ProfileScreenEvent()
    data object OnCompanyTabClick:ProfileScreenEvent()
    data object OnEditFirstNameClick:ProfileScreenEvent()
    data object OnCancelFirstNameEditClick:ProfileScreenEvent()
    data object OnDoneEditFirstNameClick:ProfileScreenEvent()
    data object OnEditLastNameClick:ProfileScreenEvent()
    data object OnEditLastNameCancelClick:ProfileScreenEvent()
    data object OnDoneEditLastName:ProfileScreenEvent()
    data object OnLogoutClick:ProfileScreenEvent()

    data class OnGetArgumentData(val companyId:String, val userId:String):ProfileScreenEvent()
    data class OnProfileUriResult(val uri: Uri?):ProfileScreenEvent()
    data class OnCompanyLogoUriResult(val uri:Uri?):ProfileScreenEvent()
    data class OnAnimateScrollToPage(val page:Int):ProfileScreenEvent()
    data class OnUpdateCompany(val update:String):ProfileScreenEvent()
    data class OnUpdateUser(val update:String):ProfileScreenEvent()
    data class OnFirstNameChange(val fistName:String):ProfileScreenEvent()
    data class OnLastNameChange(val lastName:String):ProfileScreenEvent()
}