package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class FetchProductionLines(
    private val repository:CompaniesRepository
) {
   suspend fun execute(
        onResult:(List<ProductionLine>)->Unit,
        onFailure:(String)->Unit,
        companyID:String
    )
    {
       repository.fetchProductionLines(
           companyID = companyID,
           onResult={productionLines ->  onResult(productionLines)},
           onFailure = {e-> onFailure(e)}
       )
    }
}