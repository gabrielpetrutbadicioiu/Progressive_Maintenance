package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.InterventionParticipants

class OnSelectInterventionParticipant {

    fun execute(
        selectedEmployee:UserDetails,
        participantList:List<UserDetails>,
        onFailure:(String)->Unit,
        onSuccess:(List<UserDetails>, List<InterventionParticipants>)->Unit
        )
    {
        if (participantList.isNotEmpty())
        {
            participantList.forEach { employee->
                if (employee==selectedEmployee)
                {
                    onFailure("This colleague is already on the list.")
                    return
                }
            }
        }

        val updatedParticipantList=participantList+selectedEmployee
        val interventionParticipants=updatedParticipantList.map { participant->
            InterventionParticipants(
                firstName = participant.firstName,
                lastName = participant.lastName,
                avatar = participant.profilePicture,
                id = participant.userID
                )
        }
        onSuccess(updatedParticipantList, interventionParticipants)
    }
}