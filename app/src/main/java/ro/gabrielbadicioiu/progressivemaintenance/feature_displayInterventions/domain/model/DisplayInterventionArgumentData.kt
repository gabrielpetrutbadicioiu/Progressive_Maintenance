package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.model

data class DisplayInterventionArgumentData(
    val displayAllInterventions:Boolean=false,
    val displayLineInterventions:Boolean=false,
    val displayEquipmentInterventions:Boolean=false,
    val companyId:String="",
    val userId:String="",
    val lineId:String="",
    val equipmentId:String="",
)
