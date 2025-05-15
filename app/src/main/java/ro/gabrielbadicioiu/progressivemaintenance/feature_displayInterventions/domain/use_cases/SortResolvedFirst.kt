package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

class SortResolvedFirst {
    fun execute(pmCardList: List<ProgressiveMaintenanceCard>): List<ProgressiveMaintenanceCard> {
        return pmCardList.sortedByDescending { card -> card.resolved }
    }
}