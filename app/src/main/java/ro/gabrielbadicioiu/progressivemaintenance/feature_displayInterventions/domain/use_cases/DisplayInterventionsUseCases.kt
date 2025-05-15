package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.use_cases

data class DisplayInterventionsUseCases(
    val fetchInterventions: FetchInterventions,
    val sortInterventionsByDate: SortInterventionsByDate,
    val sortInterventionsByDuration: SortInterventionsByDuration,
    val sortUnresolvedFirst: SortUnresolvedFirst,
    val sortResolvedFirst: SortResolvedFirst,
    val onSearchIntervention: OnSearchIntervention
)
