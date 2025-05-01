package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails

sealed class LogInterventionScreenEvent
{
    data object OnExpandShiftDropDown:LogInterventionScreenEvent()
    data object OnShiftDropdownDismiss:LogInterventionScreenEvent()
    data object OnExpandOtherParticipantsDropdownMenu:LogInterventionScreenEvent()
    data object OnOtherParticipantsDropdownMenuDismiss:LogInterventionScreenEvent()
    data object OnNavigateToHome:LogInterventionScreenEvent()


    data class GetArgumentData(val companyId:String, val userId:String):LogInterventionScreenEvent()
    data class OnShiftClick(val shift:String):LogInterventionScreenEvent()
    data class OnParticipantClick(val participant:UserDetails):LogInterventionScreenEvent()
    data class OnRemoveParticipant(val participant: UserDetails):LogInterventionScreenEvent()
}