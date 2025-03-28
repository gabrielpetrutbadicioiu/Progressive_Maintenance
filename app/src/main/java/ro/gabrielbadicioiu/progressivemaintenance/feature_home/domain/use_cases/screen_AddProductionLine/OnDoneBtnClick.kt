package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class OnDoneBtnClick(
    private val repository: CompaniesRepository
){
    suspend fun execute(
        productionLine: ProductionLine,
        companyID:String,
        onSuccess:()->Unit,
        onFailure:(String)->Unit,
        onInvalidName:(String)->Unit
    ){
        if (productionLine.lineName.isEmpty())
        {
            onInvalidName("Production line name is mandatory")
            return
        }
        else{
            val cleanList= productionLine.equipments.filter { equipment -> equipment.name.isNotEmpty()  }
            val newList= productionLine.copy(equipments = cleanList)
          repository.addProductionLineToCompany(
              companyID = companyID,
              productionLine = newList,
              onSuccess = {onSuccess()},
              onFailure = {e->
                  onFailure(e)
              }
          )
        }
    }
}