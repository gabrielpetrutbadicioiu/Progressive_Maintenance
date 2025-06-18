package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_editProcedure

data class EditProcedureUiErrState(
    val isFetchProcedureErr:Boolean=false,
    val isFetchAuthorErr:Boolean=false,
    val isFetchProdLineErr:Boolean=false,
    val isFetchCompanyErr:Boolean=false,
    val isEmptyProcedureNameErr:Boolean=false,
    val isStepDescriptionErr:Boolean=false,
    val errMsg:String=""
)
