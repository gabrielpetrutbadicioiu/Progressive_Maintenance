package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserProfile

import android.net.Uri

sealed class CreateUserProfileScreenEvent {
    data class OnUriResult(val uri: Uri?):CreateUserProfileScreenEvent()
    data class OnFirstNameChange(val name:String):CreateUserProfileScreenEvent()
    data class OnLastNameChange(val name:String):CreateUserProfileScreenEvent()
    data class OnPositionChange(val position:String):CreateUserProfileScreenEvent()
    data class OnFinishBtnClick(val companyID:String):CreateUserProfileScreenEvent()
    data class OnGetCurrentUser(val userID:String, val email:String):CreateUserProfileScreenEvent()

    data object OnNavigateUp:CreateUserProfileScreenEvent()


}