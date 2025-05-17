package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.InterventionParticipants

class OnSelectInterventionParticipant {

    fun execute(
        selectedEmployee:UserDetails,
        participantList:List<InterventionParticipants>,
        onFailure:(String)->Unit,
        onSuccess:( List<InterventionParticipants>)->Unit
        )
    {
        if (participantList.isNotEmpty())
        {
            participantList.forEach { employee->
                if (employee.id==selectedEmployee.userID)
                {
                    onFailure("This colleague is already on the list.")
                    return
                }
            }
        }
        val newParticipant=InterventionParticipants(
            firstName = selectedEmployee.firstName,
            lastName = selectedEmployee.lastName,
            avatar = selectedEmployee.profilePicture,
            id = selectedEmployee.userID,
            rank = selectedEmployee.rank
        )
        val updatedParticipantList=participantList+newParticipant

        onSuccess(updatedParticipantList)
    }
}