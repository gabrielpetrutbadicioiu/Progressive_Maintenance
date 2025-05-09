package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model

import com.google.firebase.firestore.Exclude

data class ProductionLine(
    val lineName:String="",
    val id:String="",
    @get:Exclude
    val isExpanded:Boolean=false,
    var equipments:List<Equipment> = listOf(Equipment(id = (1..15).map{('0'..'9').random()}.joinToString(""))),
    val addedModifiedByUserId:String=""){
    fun toFirebaseDocument() :HashMap<String, Any>{
        return hashMapOf(
            "lineName" to lineName,
            "id" to id,
            "equipments" to equipments.map
            { equipment -> hashMapOf(
                "name" to equipment.name
            )  }
        )
    }
}
