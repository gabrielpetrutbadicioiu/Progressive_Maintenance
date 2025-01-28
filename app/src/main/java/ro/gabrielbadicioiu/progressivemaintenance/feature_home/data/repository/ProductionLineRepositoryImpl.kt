package ro.gabrielbadicioiu.progressivemaintenance.feature_home.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.repository.ProductionLineRepository

class ProductionLineRepositoryImpl:ProductionLineRepository {
    override suspend fun addProductionLine(
        db: FirebaseFirestore,
        collection: String,
        document: HashMap<String, Any>,
        productionLine: ProductionLine,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,

    ) {
           try {
               db.collection(collection).add(document).await()
               onSuccess()
           }catch (e:FirebaseFirestoreException)
           {
               onFailure("Firebase error: ${e.message ?:"Unknown firebase error"}")
           }
            catch (e:Exception)
            {
                onFailure("Error: ${e.message?:"Unknown error"}")
            }
    }

    override suspend fun fetchProductionLines(
        db: FirebaseFirestore,
        collection: String,
        onSuccess: (List<ProductionLine>) -> Unit,
        onFailure: (String) -> Unit,
    ) {

        db.collection(collection).addSnapshotListener { value, error ->
            if (error!=null)
            {
                onFailure("Listen failed: ${error.message}")
                return@addSnapshotListener
            }
            if (value!=null)
            {
                val productionLines=value.documents.mapNotNull { doc->
                    doc.toObject(ProductionLine::class.java)?.copy(id = doc.id)
                }
                onSuccess(productionLines)
            }
        }
        }
    }

