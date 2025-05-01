package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails

class OnSelectInterventionParticipant {

    fun execute(
        selectedEmployee:UserDetails,
        participantList:List<UserDetails>,
        onFailure:(String)->Unit,
        onSuccess:(List<UserDetails>)->Unit
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
        onSuccess(updatedParticipantList)
    }
}