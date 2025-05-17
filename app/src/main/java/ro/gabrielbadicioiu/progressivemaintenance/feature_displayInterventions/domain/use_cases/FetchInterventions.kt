package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

class FetchInterventions(private val repository: CompaniesRepository)
{
    suspend fun execute(displayAllInterventions:Boolean,
                        displayLineInterventions:Boolean,
                        displayEquipmentInterventions:Boolean,
                        companyId:String,
                        lineId:String,
                        equipmentId:String,
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
        if (displayLineInterventions)
        {
            repository.fetchLineInterventions(
                companyId = companyId,
                lineId = lineId,
                onSuccess = {pmcList-> onResult(pmcList)},
                onFailure = {e-> onFailure(e)}
            )
        }
        if (displayEquipmentInterventions)
        {
            repository.fetchLineInterventions(
                companyId = companyId,
                lineId = lineId,
                onSuccess = {pmcList->
                    val equipmentPmcList= pmcList.filter { pmc-> pmc.equipmentId==equipmentId }
                                onResult(equipmentPmcList)
                            },
                onFailure = {e-> onFailure(e)}
            )
        }
    }
}