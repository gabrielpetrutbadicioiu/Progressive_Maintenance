package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model

data class CreateProcedureScreenErrState(
    val errMsg:String="",
    val isFetchDataErr:Boolean=false,
    val isProcedureNameErr:Boolean=false,
    val isStepDescriptionErr:Boolean=false
)
