package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_companyDetails

import com.google.firebase.firestore.CollectionReference
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class OnRegisterCompany(
    private val repository: CompaniesRepository
) {

    suspend fun execute(
        company: Company,
        onSuccess:(String)->Unit,
        onFailure:(String)->Unit,
        collectionReference: CollectionReference
    )
    {
        repository.registerCompany(
            collectionReference = collectionReference,
            company = company,
            onSuccess = {documentID-> onSuccess(documentID)},
            onFailure = {e-> onFailure(e)}
            )
    }
}