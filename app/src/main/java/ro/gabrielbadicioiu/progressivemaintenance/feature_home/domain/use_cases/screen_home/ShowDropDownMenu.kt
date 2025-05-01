package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class ShowDropDownMenu {

    fun execute(
        productionLineList:List<ProductionLine>,
        lineIndex:Int,
        clickedEquipment:Equipment
    ) :List<ProductionLine>
    {
        val prodLineList:MutableList<ProductionLine> = productionLineList.toMutableList()
        val updatedEquipments= prodLineList[lineIndex].equipments.map { equipment ->
            if (clickedEquipment==equipment)
            {
                equipment.copy(isExpanded = true)
            }
            else{
                equipment.copy(isExpanded = false)
            }
        }
        val updatedLine=productionLineList[lineIndex].copy(equipments = updatedEquipments)
        prodLineList[lineIndex]=updatedLine
        return prodLineList.toList()
    }
}