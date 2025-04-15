package ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.presentation

sealed class ProfileScreenEvent {
    data object OnNavigateHome:ProfileScreenEvent()
    data object OnNavigateToMembers:ProfileScreenEvent()
    data object OnGetLoggedUser:ProfileScreenEvent()
    data object OnAddProductionLineClick:ProfileScreenEvent()

    data class OnGetArgumentData(val companyId:String, val userId:String):ProfileScreenEvent()
}