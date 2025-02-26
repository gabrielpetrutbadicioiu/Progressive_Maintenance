package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_OwnerAccDetailsScreen

import android.net.Uri

sealed class OwnerAccDetailsScreenEvent {

    data object OnNavigateUp:OwnerAccDetailsScreenEvent()

    data class OnFirstNameChange(val firstName:String):OwnerAccDetailsScreenEvent()
    data class OnLastNameChange(val lastname:String):OwnerAccDetailsScreenEvent()
    data class OnPositionChange(val position:String):OwnerAccDetailsScreenEvent()
    data class OnUriResult(val uri: Uri?):OwnerAccDetailsScreenEvent()
    data class OnFinishBtnClick(val companyID:String):OwnerAccDetailsScreenEvent()
    data class GetUserEmailAndID(val currentUserEmail:String, val currentUserID:String):OwnerAccDetailsScreenEvent()
}