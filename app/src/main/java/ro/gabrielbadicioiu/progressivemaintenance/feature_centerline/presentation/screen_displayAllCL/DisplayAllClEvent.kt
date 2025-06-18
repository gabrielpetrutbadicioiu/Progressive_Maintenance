package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_displayAllCL

sealed class DisplayAllClScreenEvent {

    data object OnNavigateHome:DisplayAllClScreenEvent()

    data class OnGetArgumentData(
        val companyId:String,
        val userId:String,
        val productionLineId: String,
        val equipmentId: String,
    ):DisplayAllClScreenEvent()
    data class OnClClick(val clIndex:Int):DisplayAllClScreenEvent()
    data class OnQueryChange(val query:String):DisplayAllClScreenEvent()
}