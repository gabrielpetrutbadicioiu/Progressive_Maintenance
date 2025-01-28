package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model

data class ProductionLine(
    val lineName:String="",
    val id:String="",
    val isExpanded:Boolean=false,
    val equipments:List<Equipment> = listOf(Equipment())){
    fun toFirebaseDocument() :HashMap<String, Any>{
        return hashMapOf(
            "lineName" to lineName,
            "equipments" to equipments.map
            { equipment -> hashMapOf(
                "name" to equipment.name
            )  }
        )
    }
}
