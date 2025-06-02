package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model

data class Procedure(
    val date:String="",
    val authorId:String="",
    val procedureId:String="",
    val companyId:String="",
    val lineId:String="",
    val equipmentId:String="",
    val procedureName:String="",
    val steps:List<ProcedureStep> = listOf(ProcedureStep())
)
