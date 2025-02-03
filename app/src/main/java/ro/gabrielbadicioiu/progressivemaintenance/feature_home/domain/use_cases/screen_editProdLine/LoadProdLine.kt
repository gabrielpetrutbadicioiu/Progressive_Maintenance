package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine

import com.google.firebase.firestore.FirebaseFirestore
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseCollections
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.repository.ProductionLineRepository

class LoadProdLine(
    private val repository: ProductionLineRepository
){

    suspend fun execute(
        id:String,
        db:FirebaseFirestore,
        onSuccess:(ProductionLine?)->Unit,
        onFailure:(String)->Unit){
        repository.fetchProductionLines(
            db = db,
            collection = FirebaseCollections.PRODUCTION_LINES,
            onSuccess = { prodLineList->

                val editedProdLine=prodLineList.find { productionLine -> productionLine.id==id }
                if(editedProdLine==null)
                {
                    onFailure("An error occurred!")
                }
                else {
                    onSuccess(editedProdLine)
                }

            },
            onFailure = {onFailure("Failed to load production line!")}
        )
    }
}