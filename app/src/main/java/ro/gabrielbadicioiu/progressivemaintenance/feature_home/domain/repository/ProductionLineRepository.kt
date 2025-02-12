package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.repository

import com.google.firebase.firestore.FirebaseFirestore
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

interface ProductionLineRepository {

    suspend fun addProductionLine(
        db:FirebaseFirestore,
        collection:String,
        document: HashMap<String, Any>,
        productionLine: ProductionLine,
        onSuccess:()->Unit,
        onFailure:(String)->Unit,
    )
    suspend fun fetchProductionLines(
        db:FirebaseFirestore,
        collection: String,
        onSuccess: (List<ProductionLine>) -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun updateProductionLine(
        db: FirebaseFirestore,
        collection: String,
        documentID:String,
        updatedLine: HashMap<String, Any>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun deleteProductionLine(
        db: FirebaseFirestore,
        collection: String,
        documentID: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}