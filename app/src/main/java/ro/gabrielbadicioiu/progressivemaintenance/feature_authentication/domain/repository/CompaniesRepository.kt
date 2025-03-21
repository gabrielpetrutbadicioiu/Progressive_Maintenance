package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository

import com.google.firebase.firestore.CollectionReference
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails

interface CompaniesRepository {
    suspend fun registerCompany(
        collectionReference: CollectionReference,
        company: Company,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun addUserToCompany(
        companyID: String,
        user: UserDetails,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun getAllCompanies(
        collectionReference: CollectionReference,
        onSuccess: (List<Company>) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun getUserInCompany(
        currentUserID:String,
        companyID:String,
        onSuccess: (UserDetails?) -> Unit,
        onFailure: (String) -> Unit,
        onUserNotFound:()->Unit
    )
}