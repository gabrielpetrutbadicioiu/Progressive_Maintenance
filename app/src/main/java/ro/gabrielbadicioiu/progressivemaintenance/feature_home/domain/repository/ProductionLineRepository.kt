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
}