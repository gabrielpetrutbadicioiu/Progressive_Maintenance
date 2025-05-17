package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.PmCardErrorState
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

class OnResolvedInterventionCheck {
    fun execute(pmc:ProgressiveMaintenanceCard,
                onSuccess:()->Unit):PmCardErrorState
    {
        if (pmc.rootCause.isEmpty())
        {
            return PmCardErrorState(isRootCauseErr = true, errMsg = "Provide the root cause  before marking as resolved.")
        }
        if (pmc.measureTaken.isEmpty())
        {
            return PmCardErrorState(isCorrectiveMeasureErr = true, errMsg ="Corrective action is required to prevent recurrence.")
        }
        if (pmc.troubleshootingSteps.isEmpty())
        {
            return PmCardErrorState(isRepairStepsErr = true, errMsg = "Provide quick repair steps before marking as resolved.")
        }
        onSuccess()
        return PmCardErrorState()
    }
}