package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.use_cases

import android.os.Build
import androidx.annotation.RequiresApi
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SortInterventionsByDate {
    @RequiresApi(Build.VERSION_CODES.O)
    fun execute(pmCardList:List<ProgressiveMaintenanceCard>):List<ProgressiveMaintenanceCard>
    {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        return pmCardList.sortedByDescending { card ->
            try {
                LocalDateTime.parse("${card.downtimeStartDate} ${card.downtimeStartTime}", formatter)
            } catch (e: Exception) {
                LocalDateTime.MIN
            }
        }
    }
}