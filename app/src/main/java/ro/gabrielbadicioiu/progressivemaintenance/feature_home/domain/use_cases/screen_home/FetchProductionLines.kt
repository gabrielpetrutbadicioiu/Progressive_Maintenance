package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home

import com.google.firebase.firestore.FirebaseFirestore
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.repository.ProductionLineRepository

class FetchProductionLines(
    private val repository: ProductionLineRepository
) {
   suspend fun execute(
        db:FirebaseFirestore,
        collection:String,
        onSuccess:(List<ProductionLine>)->Unit,
        onFailure:(String)->Unit
    )
    {
        repository.fetchProductionLines(
            db = db,
            collection = collection,
            onSuccess = {result-> onSuccess(result)},
            onFailure = {e-> onFailure(e)}
        )
    }
}