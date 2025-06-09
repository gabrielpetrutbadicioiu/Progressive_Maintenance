package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_visualizeProcedure

data class VisualizeProcedureUiErrState(
    val isFetchProcedureErr:Boolean=false,
    val isFetchProductionLineErr:Boolean=false,
    val isFetchAuthorErr:Boolean=false,
    val errMsg:String=""
)
