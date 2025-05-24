package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model

data class CenterLineForm (
    val clName:String="",
    val clParameterList: List<CenterLineParameter> = listOf(CenterLineParameter(parameterName = "", parameterValue = "")),
    val clFormId:String="",
    val equipmentId:String="",
    val date:String="",
    val lineId:String="",
    val authorId:String="",
)