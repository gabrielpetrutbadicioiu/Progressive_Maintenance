package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine

import com.google.firebase.firestore.FirebaseFirestore
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.repository.ProductionLineRepository

class OnDoneBtnClick(
    private val repository: ProductionLineRepository
){
    suspend fun execute(
        db:FirebaseFirestore,
        collection:String,
        productionLine: ProductionLine,
        onSuccess:()->Unit,
        onFailure:(String)->Unit,
        onInvalidName:(String)->Unit
    ){
        if (productionLine.lineName.isEmpty())
        {
            onInvalidName("Production line name is mandatory")
            return
        }
        else{
            val cleanList= productionLine.equipments.filter { equipment -> equipment.name.isNotEmpty()  }
            val newList= productionLine.copy(equipments = cleanList)
            val document= newList.toFirebaseDocument()
          repository.addProductionLine(
              db = db,
              collection = collection,
              document = document,
              productionLine = newList,
              onSuccess = {onSuccess()},
              onFailure = {e-> onFailure(e)}
          )
        }
    }
}