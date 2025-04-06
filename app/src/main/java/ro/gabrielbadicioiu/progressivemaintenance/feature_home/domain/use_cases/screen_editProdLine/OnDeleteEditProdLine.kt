package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class OnDeleteEditProdLine (
    private val repository: CompaniesRepository
){
    suspend fun execute(
        deletedProductionLine: ProductionLine,
        companyId:String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    {
        repository.deleteProductionLine(
            companyId = companyId,
            productionLine =deletedProductionLine ,
            onFailure = {e->onFailure(e)},
            onSuccess={onSuccess()}
        )
    }
}