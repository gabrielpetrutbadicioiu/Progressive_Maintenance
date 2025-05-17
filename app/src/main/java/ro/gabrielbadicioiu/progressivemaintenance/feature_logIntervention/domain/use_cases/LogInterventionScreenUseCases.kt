package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases

data class LogInterventionScreenUseCases (
    val onSelectInterventionParticipant: OnSelectInterventionParticipant,
    val onLogInterventionClick: OnLogInterventionClick,
    val onInterventionUriResult: OnInterventionUriResult,
    val onResolvedInterventionCheck: OnResolvedInterventionCheck)