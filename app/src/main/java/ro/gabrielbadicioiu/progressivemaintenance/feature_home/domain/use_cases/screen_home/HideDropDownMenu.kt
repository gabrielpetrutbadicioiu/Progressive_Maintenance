package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class HideDropDownMenu {
    fun execute(
        prodLineList:List<ProductionLine>,
        clickedLine:ProductionLine,
        clickedLineIndex:Int
        ):List<ProductionLine>
    {
        val equipments=clickedLine.equipments.map { equipment ->
            equipment.copy(isExpanded = false)
        }
        val updatedLine=clickedLine.copy(equipments = equipments)
        val lineList=prodLineList.toMutableList()
        lineList[clickedLineIndex]=updatedLine.copy()
        return lineList.toList()
    }
}