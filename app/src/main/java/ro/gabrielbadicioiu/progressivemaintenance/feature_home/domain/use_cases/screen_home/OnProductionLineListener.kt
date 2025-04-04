package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class OnProductionLineListener(
    private val repository: CompaniesRepository
) {
    suspend fun execute(
        companyId:String,
        onProductionLineAdded: (addedLineId: String) -> Unit,
        onProductionLineRemoved: (removedLineId: String) -> Unit,
        onProductionLineUpdated: (updatedLineId: String) -> Unit,
        onFailure: (String) -> Unit,
    )
    {
        try {
            repository.productionLinesListener(
                companyID = companyId,
                onProductionLineAdded = {addedLineId ->
                    onProductionLineAdded(addedLineId)
                },
                onProductionLineUpdated={updatedLineId ->
                    onProductionLineUpdated(updatedLineId)
                },
                onProductionLineRemoved = {removedLineId ->
                    onProductionLineRemoved(removedLineId)
                },
                onFailure = {e-> onFailure(e)}
            )
        }catch (e:Exception)
        {
            onFailure(e.message.toString())
        }
    }
}