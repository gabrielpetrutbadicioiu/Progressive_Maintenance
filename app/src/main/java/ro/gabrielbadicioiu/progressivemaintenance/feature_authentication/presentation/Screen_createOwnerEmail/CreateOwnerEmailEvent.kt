package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail

sealed class CreateOwnerEmailEvent {

    data class OnEmailChange(val email:String):CreateOwnerEmailEvent()

    data object OnExpandTextClick:CreateOwnerEmailEvent()
    data object OnNavigateToCompanyDetailsScreen:CreateOwnerEmailEvent()
    data object OnContinueBtnClick:CreateOwnerEmailEvent()
}