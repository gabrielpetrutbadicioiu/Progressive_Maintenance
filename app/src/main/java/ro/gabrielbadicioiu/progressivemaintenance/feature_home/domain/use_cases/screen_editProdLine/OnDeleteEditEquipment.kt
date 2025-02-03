package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class OnDeleteEditEquipment {
    fun execute(productionLine: ProductionLine, index:Int):ProductionLine
    {
        val equipments=productionLine.equipments.filterIndexed { i, _ ->
            i!=index
        }
        return productionLine.copy(equipments = equipments)
    }}
