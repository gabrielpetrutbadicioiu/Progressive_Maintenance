package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.presentation.screen_displayAllIPMCards

sealed class DisplayAllPmCardsScreenEvent {

    data object OnNavigateHome:DisplayAllPmCardsScreenEvent()
    data object OnGetAllPmCards:DisplayAllPmCardsScreenEvent()
    data object OnSortInterventionsByDate:DisplayAllPmCardsScreenEvent()
    data object OnSortInterventionsByDuration:DisplayAllPmCardsScreenEvent()
    data object OnSortUnresolvedFirst:DisplayAllPmCardsScreenEvent()
    data object OnSortResolvedFirst:DisplayAllPmCardsScreenEvent()

    data class OnExpandInterventionClick(val index:Int):DisplayAllPmCardsScreenEvent()
    data class OnQueryChange(val query:String):DisplayAllPmCardsScreenEvent()
    data class OnGetArgumentData(
        val displayAllInterventions:Boolean,
        val displayLineInterventions:Boolean,
        val displayEquipmentInterventions:Boolean,
        val companyId:String,
        val userId:String,
        val lineId:String,
        val equipmentId:String,
    ):DisplayAllPmCardsScreenEvent()

}