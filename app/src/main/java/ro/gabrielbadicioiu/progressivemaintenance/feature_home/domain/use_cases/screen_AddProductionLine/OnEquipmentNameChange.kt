package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class OnEquipmentNameChange {
    fun execute(newName:String, index:Int, productionLine: ProductionLine):ProductionLine
    {
        val upName=newName.replaceFirstChar { firstChar-> firstChar.uppercase() }
       val lineEquipments= productionLine.equipments.toMutableList()

        lineEquipments.set(index = index, element = Equipment(name = upName))
        return productionLine.copy(equipments = lineEquipments)
    }
}