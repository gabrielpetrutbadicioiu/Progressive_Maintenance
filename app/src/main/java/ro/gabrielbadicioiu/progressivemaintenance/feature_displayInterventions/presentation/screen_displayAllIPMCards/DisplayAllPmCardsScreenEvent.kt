package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.presentation.screen_displayAllIPMCards

import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

sealed class DisplayAllPmCardsScreenEvent {

    data object OnNavigateHome:DisplayAllPmCardsScreenEvent()
    data object OnGetAllPmCards:DisplayAllPmCardsScreenEvent()
    data object OnSortInterventionsByDate:DisplayAllPmCardsScreenEvent()
    data object OnSortInterventionsByDuration:DisplayAllPmCardsScreenEvent()
    data object OnSortUnresolvedFirst:DisplayAllPmCardsScreenEvent()
    data object OnSortResolvedFirst:DisplayAllPmCardsScreenEvent()
    data object OnConfirmBtnClick:DisplayAllPmCardsScreenEvent()
    data object OnDismissDialogClick:DisplayAllPmCardsScreenEvent()

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
    data class OnViewInterventionDetailsClick(val pmc:ProgressiveMaintenanceCard):DisplayAllPmCardsScreenEvent()
    data class OnDeleteInterventionClick(val pmcId:String):DisplayAllPmCardsScreenEvent()

}