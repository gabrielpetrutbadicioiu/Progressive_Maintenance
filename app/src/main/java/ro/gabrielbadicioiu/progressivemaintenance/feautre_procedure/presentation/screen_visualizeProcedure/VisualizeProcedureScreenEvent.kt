package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_visualizeProcedure

sealed class VisualizeProcedureScreenEvent {

    data object OnNavigateToDisplayAllProcedureScreen:VisualizeProcedureScreenEvent()
    data object OnEditBtnClick:VisualizeProcedureScreenEvent()
    data class OnFetchArgumentData(
        val userId:String,
        val companyId:String,
        val productionLineId:String,
        val equipmentId:String,
        val procedureId:String,
    ):VisualizeProcedureScreenEvent()

    data class OnPhoto1Click(val index:Int):VisualizeProcedureScreenEvent()
    data class OnPhoto2Click(val index: Int):VisualizeProcedureScreenEvent()
    data class OnPhoto3Click(val index:Int):VisualizeProcedureScreenEvent()

}