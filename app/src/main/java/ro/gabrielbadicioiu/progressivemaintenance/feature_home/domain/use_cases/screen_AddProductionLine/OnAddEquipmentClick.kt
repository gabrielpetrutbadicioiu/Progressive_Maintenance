package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class OnAddEquipmentClick {
    fun execute(productionLine:ProductionLine):ProductionLine
    {
        val id= (1..15).map{('0'..'9').random()}.joinToString("")
        return productionLine.copy(equipments = productionLine.equipments+Equipment(id = id))
    }
}