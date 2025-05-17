package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model

data class PmCardErrorState(
    val isShiftErr:Boolean=false,
    val isStartDateErr:Boolean=false,
    val isEndDateErr:Boolean=false,
    val isStartTimeErr:Boolean=false,
    val isEndTimeErr:Boolean=false,
    val isEndTimeBeforeStart:Boolean=false,
    val isProblemDescrErr:Boolean=false,
    val isProblemDetailErr:Boolean=false,
    val isRootCauseErr:Boolean=false,
    val isCorrectiveMeasureErr:Boolean=false,
    val isRepairStepsErr:Boolean=false,
    val errMsg:String=""
)
