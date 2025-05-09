package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model

import com.google.firebase.firestore.Exclude
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment

data class ProgressiveMaintenanceCard(
    val authorId:String="",
    val companyId:String="",
    val interventionId:String="",
    val productionLineId:String="",
    val equipmentId:String="",
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
    val measureTaken:String="",
    val photo1:String="",
    val photo2:String="",
    val photo3:String="",
    val photo1Name:String="",
    val photo2Name:String="",
    val photo3Name:String="",
    val isResolved:Boolean=false,

)
