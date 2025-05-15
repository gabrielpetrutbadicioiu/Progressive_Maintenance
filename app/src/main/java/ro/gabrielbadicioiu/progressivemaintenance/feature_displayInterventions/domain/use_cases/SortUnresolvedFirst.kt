package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

class SortUnresolvedFirst {
    fun execute(pmCardList: List<ProgressiveMaintenanceCard>): List<ProgressiveMaintenanceCard> {
        return pmCardList.sortedBy { card -> card.resolved }
    }
}