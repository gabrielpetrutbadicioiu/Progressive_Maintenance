package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard
class SortInterventionsByDuration {
    fun execute(pmcList: List<ProgressiveMaintenanceCard>): List<ProgressiveMaintenanceCard> {
        return pmcList.sortedByDescending { card ->
            try {
                val text = card.totalDowntimeDuration.trim()

                val hourRegex = Regex("(\\d+)h")
                val minuteRegex = Regex("(\\d+)m")

                val hours = hourRegex.find(text)?.groupValues?.get(1)?.toIntOrNull() ?: 0
                val minutes = minuteRegex.find(text)?.groupValues?.get(1)?.toIntOrNull() ?: 0

                hours * 60 + minutes
            } catch (e: Exception) {
                0
            }
        }
    }
}
