package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

class FetchInterventions(private val repository: CompaniesRepository)
{
    suspend fun execute(displayAllInterventions:Boolean,
                        displayLineInterventions:Boolean,
                        displayEquipmentInterventions:Boolean,
                        companyId:String,
                        onResult:( List<ProgressiveMaintenanceCard>)->Unit,
                        onFailure:(String)->Unit)
    {
        if (displayAllInterventions)
        {
            try {
                repository.getGlobalInterventions(
                    companyID = companyId,
                    onSuccess = {interventions-> onResult(interventions)},
                    onFailure = {e-> onFailure(e)}
                )
            }catch (e:Exception)
            {
                onFailure(e.message?:"Use case err: failed to fetch global interventions")
            }
        }
    }
}