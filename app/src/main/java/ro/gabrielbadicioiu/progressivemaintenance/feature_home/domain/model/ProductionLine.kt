package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model

data class ProductionLine( val lineName:String="", val equipments:MutableList<Equipment> = mutableListOf(Equipment()))
