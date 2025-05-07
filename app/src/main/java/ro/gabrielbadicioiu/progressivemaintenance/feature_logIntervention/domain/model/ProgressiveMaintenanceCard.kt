package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model

data class ProgressiveMaintenanceCard(
    val authorId:String="",
    val companyId:String="",
    val authorAvatar:String="",
    val otherParticipants:List<InterventionParticipants> = emptyList(),
    val shift:String="",
    val downtimeStartDate:String="",
    val downtimeEndDate:String="",
    val downtimeStartTime:String="",
    val downtimeEndTime:String="",
    val totalDowntimeDuration:String="",
    val problemDescription:String="",
    val problemDetailing:String="",
    val troubleshootingSteps:String="",
    val rootCause:String="",
    val observations:String="",
    val isResolved:Boolean=false,

)
