package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository

import com.google.firebase.firestore.CollectionReference
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

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

    suspend fun addProductionLineToCompany(
        companyID:String,
        productionLine: ProductionLine,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun fetchProductionLines(
        companyID: String,
        onResult: (List<ProductionLine>)->Unit,
        onFailure:(String)->Unit
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
    suspend fun getProductionLineById(
        companyId: String,
        productionLineId:String,
        onSuccess: ( ProductionLine) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun productionLinesListener(
        companyID: String,
        onProductionLineAdded:(addedLineId:String)->Unit,
        onProductionLineRemoved:(removedLineId:String)->Unit,
        onProductionLineUpdated:(updatedLineId:String)->Unit,
        onFailure:(String)->Unit
    )

}