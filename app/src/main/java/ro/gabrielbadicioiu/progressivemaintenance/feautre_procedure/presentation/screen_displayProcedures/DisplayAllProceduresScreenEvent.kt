package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_displayProcedures

sealed class DisplayAllProceduresScreenEvent
{
    data object OnNavigateHome:DisplayAllProceduresScreenEvent()

    data class OnGetArgumentData(
        val companyId:String,
        val lineId:String,
        val equipmentId:String,
        val userId:String):DisplayAllProceduresScreenEvent()

    data class OnSearchQueryChange(val query:String):DisplayAllProceduresScreenEvent()

}