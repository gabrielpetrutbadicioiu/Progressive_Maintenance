package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestoreException

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseCollections
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseSubCollections
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

class CompaniesRepositoryImpl:CompaniesRepository {
    override suspend fun registerCompany(
        collectionReference: CollectionReference,
        company: Company,
        onSuccess:(String)->Unit,
        onFailure:(String)->Unit
        ) {
        val firebaseDocument=company.toFirebaseDocument()

       collectionReference.add(firebaseDocument)
           .addOnSuccessListener { documentReference->
               val companyID=documentReference.id
               documentReference.update("id",companyID).addOnSuccessListener {
                   onSuccess(companyID)
               }
                   .addOnFailureListener {
                       onFailure("Error updating companyID")
                   }
           onSuccess(documentReference.id)
       }
           .addOnFailureListener { e->
               onFailure("Error adding document: ${e.message}")
           }
    }

    override suspend fun addUserToCompany(
        companyID: String,
        user: UserDetails,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val companyRef=Firebase.firestore.collection(FirebaseCollections.COMPANIES).document(companyID)
        val usersCollection=companyRef.collection(FirebaseSubCollections.USERS)
        val firebaseDocument=user.toFirebaseDocument()
        usersCollection.document(user.userID).set(firebaseDocument)//adaugat user id generat de firebase
        // ID-ul userului din Firebase Auth și Firestore e același → nu mai ai nevoie de căutări extra.
        //Un user nu poate avea două înregistrări diferite în companie → e mereu unic.
       // Când vrei să iei datele unui user, știi deja ID-ul lui de la autentificare
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {e->
                onFailure("Error adding user to company: ${e.message}")
            }
    }

    override suspend fun addProductionLineToCompany(
        companyID: String,
        productionLine: ProductionLine,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val db=Firebase.firestore
      val docRef=  db.collection(FirebaseCollections.COMPANIES)//accesez colectia
            .document(companyID)//accesez documentul
            .collection(FirebaseCollections.PRODUCTION_LINES)//creaza/alege colectia
            .document()//adauga document cu id generat automat
            val prodLine=productionLine.copy(id = docRef.id)//actualizam id-ul generat
            docRef.set(prodLine)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e->
                onFailure(e.message.toString())
            }
    }

    override suspend fun productionLinesListener(
        companyID: String,
        onProductionLineAdded: (addedLineId: String) -> Unit,
        onProductionLineRemoved: (removedLineId: String) -> Unit,
        onProductionLineUpdated: (updatedLineId: String) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore.collection(FirebaseCollections.COMPANIES)
            .document(companyID)
            .collection(FirebaseCollections.COMPANIES)
            .addSnapshotListener { snapshots, error ->
                if (error!=null)
                {
                    onFailure(error.message.toString())
                    return@addSnapshotListener
                }
                if (snapshots!=null)
                {
                    for (dc in snapshots.documentChanges)//iteram prin toate documentele care s-au schimbat
                    {
                        if (dc.type==DocumentChange.Type.ADDED)
                        {
                            val addedLine=dc.document.toObject(ProductionLine::class.java)
                            onProductionLineAdded(addedLine.id)
                        }
                        if (dc.type==DocumentChange.Type.REMOVED)
                        {
                            val removedLine=dc.document.toObject(ProductionLine::class.java)
                            onProductionLineRemoved(removedLine.id)
                        }
                        if (dc.type==DocumentChange.Type.MODIFIED)
                        {
                            val modifiedLine=dc.document.toObject(ProductionLine::class.java)
                            onProductionLineUpdated(modifiedLine.id)
                        }

                    }
                }
                else{
                    onFailure("snapshot is null")
                }
            }
    }

    override suspend fun fetchProductionLines(
        companyID: String,
        onResult: (List<ProductionLine>) -> Unit,
        onFailure:(String)->Unit
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyID)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .addSnapshotListener { snapShot, error ->
                if (error!=null)
                {
                    onFailure(error.message.toString())
                }
                if (snapShot!=null)
                {
                    val productionLines=snapShot.documents.mapNotNull { documentSnapshot ->
                        val line=documentSnapshot.toObject(ProductionLine::class.java)
                        line?.copy(id = documentSnapshot.id)
                    }
                    onResult(productionLines)
                }
            }
    }

    override suspend fun getAllCompanies(
        collectionReference: CollectionReference,
        onSuccess: ( List<Company>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        try {
            collectionReference.addSnapshotListener { snapshot, error ->
                if (error!=null)
                {
                    onFailure(error.message.toString())
                    return@addSnapshotListener
                }
                if (snapshot!=null && !snapshot.isEmpty)
                {
                    val companies=snapshot.documents.mapNotNull {documentSnapshot ->
                        documentSnapshot.toObject(Company::class.java)
                    }
                    onSuccess(companies)
                }
                else{
                    onFailure("No companies found")
                }
            }
        }catch (e:FirebaseFirestoreException)
        {
            onFailure("Firebase firestore exception: ${e.message}")
        }
        catch (e:FirebaseException)
        {
            onFailure("Firebase exception: ${e.message}")
        }
        catch (e:Exception )
        {
            onFailure("Error fetching companies: ${e.message}")
        }

    }

    override suspend fun getUserInCompany(
        currentUserID: String,
        companyID: String,
        onSuccess: (UserDetails?) -> Unit,
        onFailure: (String) -> Unit,
        onUserNotFound:()->Unit
    ) {
        Firebase.firestore.collection(FirebaseCollections.COMPANIES) //accesam colectia principala
            .document(companyID)//selectam compania dupa ID
            .collection(FirebaseSubCollections.USERS)//accesam subcolectia users
            .whereEqualTo("userID", currentUserID)
            .get()
            .addOnSuccessListener {result->
                if (result.isEmpty)
                {
                    onUserNotFound()
                }
                else{
                    val userDetails=result.documents.first().toObject(UserDetails::class.java)
                    onSuccess(userDetails)
                }
            }
            .addOnFailureListener { e->
                onFailure(e.message ?: "Unknown error")
            }

    }

    override suspend fun getProductionLineById(
        companyId: String,
        productionLineId: String,
        onSuccess: ( ProductionLine) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore.collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(productionLineId)
            .get()
            .addOnSuccessListener { result->
                if (result.exists())
                {
                    val productionLine=result.toObject(ProductionLine::class.java)
                    if (productionLine!=null)
                    {
                        onSuccess(productionLine)
                    }
                    else{
                        onFailure("Production line is null")
                    }
                }
                else{
                    onFailure("Production line not found")
                }
            }
            .addOnFailureListener { e->
                onFailure(e.message.toString())
            }
    }

}