package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class OnEquipmentDelete {
    fun execute(productionLine: ProductionLine, index:Int):ProductionLine
    {
        val equipmentList=productionLine.equipments.toMutableList()
        equipmentList.removeAt(index = index)
        return productionLine.copy(equipments = equipmentList)
    }
}