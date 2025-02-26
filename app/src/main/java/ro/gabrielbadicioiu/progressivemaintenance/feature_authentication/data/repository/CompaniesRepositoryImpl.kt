package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestoreException

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseCollections
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseSubCollections
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

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
}