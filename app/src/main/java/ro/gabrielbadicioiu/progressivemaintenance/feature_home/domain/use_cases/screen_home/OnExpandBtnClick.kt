package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class OnExpandBtnClick {
    fun execute(productionLinesList:List<ProductionLine>, id:String):List<ProductionLine>
    {
        return productionLinesList.map { line->
            if (line.id==id)
            {
                line.copy(isExpanded = !line.isExpanded)
            }
            else{
                line
            }
        }
    }
}