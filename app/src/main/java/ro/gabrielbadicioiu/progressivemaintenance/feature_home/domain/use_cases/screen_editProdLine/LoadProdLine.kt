package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class LoadProdLine(
    private val repository:CompaniesRepository
){

    suspend fun execute(
        companyId:String,
        productionLineId:String,
        onSuccess:(ProductionLine)->Unit,
        onFailure:(String)->Unit){
        repository.getProductionLineById(
            companyId = companyId,
            productionLineId = productionLineId,
            onSuccess = {productionLine ->
                onSuccess(productionLine)
            },
            onFailure = {e->
                onFailure(e)
            }
        )
    }
}