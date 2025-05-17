package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model

import com.google.firebase.firestore.Exclude

data class ProgressiveMaintenanceCard(
    val authorId:String="",
    val companyId:String="",
    val interventionId:String="",
    val productionLineId:String="",
    val equipmentId:String="",
    val productionLineName:String="",
    val equipmentName:String="",
    val authorName:String="",
    val authorAvatar:String="",
    val authorRank:String="",
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
    val measureTaken:String="",
    val photo1:String="",
    val photo2:String="",
    val photo3:String="",
    val photo1Name:String="",
    val photo2Name:String="",
    val photo3Name:String="",
    val resolved:Boolean=false,
    @get:Exclude
    val isExpanded:Boolean=false,
    @get:Exclude
    val isNewIntervention:Boolean=false,
    @get:Exclude
    val isEditing:Boolean=false
)
