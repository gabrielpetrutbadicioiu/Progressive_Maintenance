package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine

class OnProductionLineNameChange {
    fun execute(newName:String):String
    {
        val upName=newName.replaceFirstChar { firstChar-> firstChar.uppercase() }
        return upName
    }
}