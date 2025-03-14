package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core

import com.google.firebase.firestore.CollectionReference
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class FetchRegisteredCompanies(
    private val repository: CompaniesRepository
) {
    suspend fun execute(
        collectionReference: CollectionReference,
        onSuccess:(List<Company>)->Unit,
        onFailure:(String)->Unit
    ){
        repository.getAllCompanies(
            collectionReference = collectionReference,
            onSuccess = {registeredCompanies-> onSuccess(registeredCompanies)},
            onFailure = {e-> onFailure(e)}
        )
    }
}