package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model

data class ProcedureStep(
    var stepDescription:String="",
    val photo1Uri:String="",
    val photo2Uri:String="",
    val photo3Uri:String=""
)
