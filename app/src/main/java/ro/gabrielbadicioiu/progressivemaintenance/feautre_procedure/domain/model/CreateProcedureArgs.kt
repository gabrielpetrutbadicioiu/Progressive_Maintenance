package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model

data class CreateProcedureArgs(
    val companyId:String="",
    val userId:String="",
    val productionLineId: String="",
    val equipmentId: String="",
)
