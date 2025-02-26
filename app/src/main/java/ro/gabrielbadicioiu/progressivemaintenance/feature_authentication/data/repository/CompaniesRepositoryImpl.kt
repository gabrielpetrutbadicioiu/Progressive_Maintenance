package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository

import com.google.firebase.firestore.CollectionReference

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
}