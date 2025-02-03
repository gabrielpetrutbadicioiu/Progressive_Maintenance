package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine

import com.google.firebase.firestore.FirebaseFirestore
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.repository.ProductionLineRepository

class OnDoneEdit(
    private val repository: ProductionLineRepository
) {
    suspend fun execute(
        db:FirebaseFirestore,
        collection:String,
        updatedLine:ProductionLine,
        onSuccess:()->Unit,
        onFailure:(String)->Unit,
        onEmptyName:(String)->Unit
    )
    {
        //clean empty equipments fields
        val cleanEquipmentList=updatedLine.equipments.filter{equipment -> equipment.name.isNotEmpty()}
        val cleanedLine=updatedLine.copy(equipments = cleanEquipmentList)
        val document=cleanedLine.toFirebaseDocument()
        if(updatedLine.lineName.isEmpty()){
            onEmptyName("The production line name field cannot be empty.")
            return
        }
        else{
            repository.updateProductionLine(
                db = db,
                collection = collection,
                onSuccess = {onSuccess()},
                onFailure = {e-> onFailure(e)},
                documentID = updatedLine.id,
                updatedLine = document
            )
        }

    }
}