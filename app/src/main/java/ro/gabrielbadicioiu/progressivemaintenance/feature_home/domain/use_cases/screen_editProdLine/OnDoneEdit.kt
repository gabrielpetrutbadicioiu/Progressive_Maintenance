package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class OnDoneEdit(
    private val repository:CompaniesRepository
) {
    suspend fun execute(
        companyId:String,
        updatedLine:ProductionLine,
        onSuccess:()->Unit,
        onFailure:(String)->Unit,
        onEmptyName:(String)->Unit
    )
    {
        //clean empty equipments fields
        val cleanEquipmentList=updatedLine.equipments.filter{equipment -> equipment.name.isNotEmpty()}
        val cleanedLine=updatedLine.copy(equipments = cleanEquipmentList)
        if(updatedLine.lineName.isEmpty()){
            onEmptyName("The production line name field cannot be empty.")
            return
        }
        else{
           repository.updateProductionLine(
               companyId =companyId ,
               productionLine =cleanedLine ,
               onFailure ={e-> onFailure(e)} ,
               onSuccess={onSuccess()}
           )
        }

    }
}