package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model

data class CreateClArgumentData(
    val companyId:String="",
    val userId:String="",
    val lineId:String="",
    val equipmentId:String="",
    val clId:String="",
    val isCreatingNewCl:Boolean=false
)