package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

class OnSearchIntervention {
    fun execute( query:String,  pmCardList:List<ProgressiveMaintenanceCard>):List<ProgressiveMaintenanceCard>
    {
        return pmCardList.filter {pmc->
                pmc.problemDescription.contains(query, ignoreCase = true)
        }
    }
}