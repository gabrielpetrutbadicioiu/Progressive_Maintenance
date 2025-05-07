package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases

import android.os.Build
import androidx.annotation.RequiresApi
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.PmCardErrorState
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class OnLogInterventionClick {
    @RequiresApi(Build.VERSION_CODES.O)
    fun execute(pmCard: ProgressiveMaintenanceCard):PmCardErrorState
    {


        if (pmCard.shift.isEmpty())
        {
            return PmCardErrorState(isShiftErr = true, errMsg = "Please select your shift before submitting.")
        }
        if (pmCard.downtimeStartDate.isEmpty())
        {
            return PmCardErrorState(isStartDateErr = true, errMsg = "Please select downtime start date before submitting.")
        }
        if (pmCard.downtimeEndDate.isEmpty())
        {
            return PmCardErrorState(isEndDateErr = true, errMsg = "Please select downtime end date before submitting.")
        }
        if (pmCard.downtimeStartTime.isEmpty())
        {
            return PmCardErrorState(isStartTimeErr = true, errMsg = "Please select start time before submitting.")
        }
        if (pmCard.downtimeEndTime.isEmpty())
        {
            return PmCardErrorState(isEndTimeErr = true, errMsg = "Please select end time before submitting.")
        }
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        val startDate = LocalDate.parse(pmCard.downtimeStartDate, dateFormatter)
        val startTime = LocalTime.parse(pmCard.downtimeStartTime, timeFormatter)
        val endDate = LocalDate.parse(pmCard.downtimeEndDate, dateFormatter)
        val endTime = LocalTime.parse(pmCard.downtimeEndTime, timeFormatter)

        val startDateTime = LocalDateTime.of(startDate, startTime)
        val endDateTime = LocalDateTime.of(endDate, endTime)
        if (startDateTime.isAfter(endDateTime)) {
            return PmCardErrorState(isEndTimeBeforeStart = true, isEndTimeErr = true, isEndDateErr = true, isStartDateErr = true, isStartTimeErr = true, errMsg = "End date and time must be after start date and time.")
        }
        if (pmCard.problemDescription.isEmpty())
        {
            return PmCardErrorState(isProblemDescrErr = true, errMsg = "Description is missing. Please provide a brief overview of the issue (e.g., error code or behavior).")
        }
        if (pmCard.problemDetailing.isEmpty())
        {
            return PmCardErrorState(isProblemDetailErr = false, errMsg = "Please provide detailed information about the issue and how it was resolved. This field is required.")
        }

        return PmCardErrorState()

    }
}