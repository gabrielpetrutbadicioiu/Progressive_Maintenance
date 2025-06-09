package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository

import com.google.firebase.firestore.CollectionReference
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure

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

    suspend fun fetchAllUsersInCompany(
        companyID: String,
        onResult: (List<UserDetails>) -> Unit,
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

    suspend fun getProductionLineById(
        companyId: String,
        productionLineId:String,
        onSuccess: ( ProductionLine) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun getCompanyById(
        companyId: String,
        onSuccess: (company:Company) -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun productionLinesListener(
        companyID: String,
        onProductionLineAdded:(addedLineId:String)->Unit,
        onProductionLineRemoved:(removedLineId:String)->Unit,
        onProductionLineUpdated:(updatedLineId:String)->Unit,
        onFailure:(String)->Unit
    )
    suspend fun updateProductionLine(
        companyId: String,
        productionLine:ProductionLine,
        onFailure: (String) -> Unit,
        onSuccess: () -> Unit
    )
    suspend fun updateUser(
        companyId: String,
        user: UserDetails,
        onFailure: (String) -> Unit,
        onSuccess: () -> Unit
    )
    suspend fun updateCompany(
        companyId: String,
        updatedCompany: Company,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun deleteProductionLine(
        companyId: String,
        productionLine: ProductionLine,
        onFailure: (String) -> Unit,
        onSuccess: () -> Unit
    )

    suspend fun addIntervention(
        companyID: String,
        productionLineId: String,
        pmCard: ProgressiveMaintenanceCard,
        onSuccess: (interventionId:String) -> Unit,
        onFailure: (String) -> Unit,
    )

    suspend fun addInterventionGlobally(
        companyID: String,
        interventionId:String,
        pmCard: ProgressiveMaintenanceCard,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    )
    suspend fun getGlobalInterventions(
        companyID: String,
        onSuccess: ( List<ProgressiveMaintenanceCard>) -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun fetchLineInterventions(
        companyId: String,
        lineId:String,
        onSuccess: (List<ProgressiveMaintenanceCard>) -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun fetchPmCardById(
        companyId: String,
        productionLineId: String,
        interventionId: String,
        onSuccess: (pmc:ProgressiveMaintenanceCard) -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun updatePmCard(
        pmc:ProgressiveMaintenanceCard,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun saveCenterLineForm(
        companyId: String,
        lineId:String,
        equipmentId:String,
        clForm:CenterLineForm,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun fetchAllCenterLines(
        companyId:String,
        lineId:String,
        onSuccess: (List<CenterLineForm>) -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun fetchCenterLineById(
        companyId:String,
        lineId:String,
        clId:String,
        onSuccess: (cl:CenterLineForm) -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun updateCl(
        companyId:String,
        lineId:String,
        clId:String,
        cl:CenterLineForm,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
    suspend fun uploadProcedure(
        companyId: String,
        lineId: String,
        procedure: Procedure,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    suspend fun fetchAllEquipmentProcedures(
        companyId:String,
        lineId: String,
        equipmentId:String,
        onSuccess: (List<Procedure>) -> Unit,
        onFailure: (String) -> Unit
    )

}