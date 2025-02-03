package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class OnEquipmentEdit {

    fun execute(newName:String, index:Int, productionLine: ProductionLine):ProductionLine
    {
        val equipments=productionLine.equipments.mapIndexed { i, equipment ->
            if(i==index)
            {
                equipment.copy(name = newName.replaceFirstChar { char-> char.uppercase() })
            }
            else {equipment}
        }
        return productionLine.copy(equipments = equipments)

    }
}