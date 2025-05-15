package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation

import android.net.Uri
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails

sealed class LogInterventionScreenEvent
{
    data object OnExpandShiftDropDown:LogInterventionScreenEvent()
    data object OnShiftDropdownDismiss:LogInterventionScreenEvent()
    data object OnExpandOtherParticipantsDropdownMenu:LogInterventionScreenEvent()
    data object OnOtherParticipantsDropdownMenuDismiss:LogInterventionScreenEvent()
    data object OnNavigateToHome:LogInterventionScreenEvent()
    data object OnSelectStartDateClick:LogInterventionScreenEvent()
    data object OnSelectStartDateDismiss:LogInterventionScreenEvent()
    data object OnSelectEndDateClick:LogInterventionScreenEvent()
    data object OnEndDateDialogDismiss:LogInterventionScreenEvent()
    data object OnDownTimeStartDialogClick:LogInterventionScreenEvent()
    data object OnDownTimeEndDialogClick:LogInterventionScreenEvent()
    data object OnDownTimeStartDismiss:LogInterventionScreenEvent()
    data object OnDownTimeEndDismiss:LogInterventionScreenEvent()
    data object OnLogInterventionClick:LogInterventionScreenEvent()
    data object OnShowInfo:LogInterventionScreenEvent()
    data object OnDismissInfo:LogInterventionScreenEvent()
    data object OnPhoto1Delete:LogInterventionScreenEvent()
    data object OnPhoto2Delete:LogInterventionScreenEvent()
    data object OnPhoto3Delete:LogInterventionScreenEvent()
    data object OnLoadInterventionGlobally:LogInterventionScreenEvent()

    data class GetArgumentData(
        val companyId:String,
        val userId:String,
        val productionLineId: String,
        val equipmentId:String,
        val equipmentName:String,
        val prodLineName:String):LogInterventionScreenEvent()
    data class OnShiftClick(val shift:String):LogInterventionScreenEvent()
    data class OnParticipantClick(val participant:UserDetails):LogInterventionScreenEvent()
    data class OnRemoveParticipant(val participant: UserDetails):LogInterventionScreenEvent()
    data class OnGetStartDate(val startDate:String):LogInterventionScreenEvent()
    data class OnGetEndDate(val endDate:String):LogInterventionScreenEvent()
    data class OnGetDowntimeStartTime(val startTime:String):LogInterventionScreenEvent()
    data class OnGetDowntimeEndTime(val endTime:String):LogInterventionScreenEvent()
    data class OnGetTotalDowntimeDuration(val totalDowntimeDuration:String):LogInterventionScreenEvent()
    data class OnProblemDescriptionChange(val problemDescription:String):LogInterventionScreenEvent()
    data class OnProblemDetailingChange(val problemDetailing:String):LogInterventionScreenEvent()
    data class OnRootCauseChange(val rootCause:String):LogInterventionScreenEvent()
    data class OnObservationsChange(val obs:String):LogInterventionScreenEvent()
    data class OnTroubleshootStepsChange(val steps:String):LogInterventionScreenEvent()
    data class OnMeasuresTakenChange(val measuresTaken:String):LogInterventionScreenEvent()
    data class OnUriResult(val localUri: Uri):LogInterventionScreenEvent()
}