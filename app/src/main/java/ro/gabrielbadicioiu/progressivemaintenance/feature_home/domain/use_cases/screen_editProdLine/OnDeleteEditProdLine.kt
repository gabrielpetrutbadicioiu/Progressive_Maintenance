package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine

import com.google.firebase.firestore.FirebaseFirestore
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.repository.ProductionLineRepository

class OnDeleteEditProdLine (
    private val repository: ProductionLineRepository
){
    suspend fun execute(
        db:FirebaseFirestore,
        collection:String,
        documentID:String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    {
        repository.deleteProductionLine(
            db = db,
            collection = collection,
            documentID = documentID,
            onSuccess = {onSuccess()},
            onFailure = {e-> onFailure(e)}
        )
    }
}