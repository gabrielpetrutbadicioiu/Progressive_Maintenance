package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.model

data class SortOption(
    val sortByDate:Boolean=true,
    val sortByDuration:Boolean=false,
    val resolvedOnly:Boolean=false,
    val unresolvedOnly:Boolean=false
)
