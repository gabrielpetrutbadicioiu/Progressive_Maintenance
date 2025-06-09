package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestoreException

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseCollections
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseSubCollections
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure

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

    override suspend fun addIntervention(
        companyID: String,
        productionLineId: String,
        pmCard: ProgressiveMaintenanceCard,
        onSuccess: (interventionId:String) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val docRef=Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyID)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(productionLineId)
            .collection(FirebaseCollections.INTERVENTIONS)
            .document()

        val pmc=pmCard.copy(interventionId = docRef.id)
        docRef.set(pmc)
            .addOnSuccessListener { onSuccess(docRef.id) }
            .addOnFailureListener { e->
                onFailure(e.message?:"FireStore error")
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

    override suspend fun updateProductionLine(
        companyId: String,
        productionLine:ProductionLine,
        onFailure: (String) -> Unit,
        onSuccess: () -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(productionLine.id)
            .set(productionLine)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e-> onFailure(e.message.toString()) }

    }

    override suspend fun updateCompany(
        companyId: String,
        updatedCompany: Company,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .set(updatedCompany)
            .addOnSuccessListener { onSuccess()}
            .addOnFailureListener { e->
                onFailure(e.message?:"Repository:Failed to update production line")
            }
    }

    override suspend fun updateUser(
        companyId: String,
        user: UserDetails,
        onFailure: (String) -> Unit,
        onSuccess: () -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseSubCollections.USERS)
            .document(user.userID)
            .set(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e->onFailure(e.message?:"Failed to update user!") }
    }

    override suspend fun deleteProductionLine(
        companyId: String,
        productionLine: ProductionLine,
        onFailure: (String) -> Unit,
        onSuccess: () -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(productionLine.id)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e->
                onFailure(e.message?:"Null deleting error")
            }
    }

    override suspend fun fetchAllUsersInCompany(
        companyID: String,
        onResult: (List<UserDetails>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyID)
            .collection(FirebaseSubCollections.USERS)
            .addSnapshotListener { snapShot, error ->
                if (error!=null)
                {
                    onFailure(error.message?:"Failed to fetch users")
                    return@addSnapshotListener
                }
                if (snapShot!=null)
                {
                    val userList=snapShot.documents.mapNotNull { documentSnapshot ->
                        documentSnapshot.toObject(UserDetails::class.java)
                    }
                    onResult(userList)
                }
                else{
                    onFailure("Failed to fetch users, snapshot is null")
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
            .addSnapshotListener { snapshot, error ->
                if(error!=null)
                {
                    onFailure(error.message?:"Failed to load user")
                    return@addSnapshotListener
                }
                try {
                    if (snapshot!=null && !snapshot.isEmpty)
                    {
                        val user= snapshot.documents.first().toObject(UserDetails::class.java)
                        onSuccess(user)
                    }
                    else {
                        onUserNotFound()
                    }
                }catch (e:FirebaseFirestoreException)
                {
                    onFailure(e.message?:"FireStore exception:Failed to fetch user")
                }
                catch (e:FirebaseException)
                {
                    onFailure(e.message?:"Firebase exception:Failed to fetch user")
                }
                catch (e:Exception)
                {
                    onFailure(e.message?:"Exception:Failed to fetch user")
                }

            }


    }

    override suspend fun getCompanyById(
        companyId: String,
        onSuccess: (company:Company) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    onFailure(error.message ?: "Failed to fetch company!")
                    return@addSnapshotListener
                }
                if (documentSnapshot != null && documentSnapshot.exists())
                {
                    val company=documentSnapshot.toObject(Company::class.java)
                    if (company!=null)
                    {
                        onSuccess(company)
                    }
                    else{
                        onFailure("Company data is null")
                    }
                }
                else{
                    onFailure("Company not found")
                }
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

    override suspend fun addInterventionGlobally(
        companyID: String,
        interventionId:String,
        pmCard: ProgressiveMaintenanceCard,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {

        Firebase.firestore.collection(FirebaseCollections.COMPANIES)
            .document(companyID)
            .collection(FirebaseCollections.GLOBAL_INTERVENTIONS)
            .document(interventionId)
            .set(pmCard)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e-> onFailure(e.message?:"Firebase Err: Failed to upload intervention globally") }
    }

    override suspend fun getGlobalInterventions(
        companyID: String,
        onSuccess: ( List<ProgressiveMaintenanceCard>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyID)
            .collection(FirebaseCollections.GLOBAL_INTERVENTIONS)
            .addSnapshotListener { interventionsSnapshot, error ->
                if (error!=null)
                {
                    onFailure(error.message?:"Repository:Failed to fetch global interventions")
                    return@addSnapshotListener
                }
                if (interventionsSnapshot!=null && !interventionsSnapshot.isEmpty)
                {
                    val interventionList=interventionsSnapshot.mapNotNull { queryDocumentSnapshot ->
                        queryDocumentSnapshot.toObject(ProgressiveMaintenanceCard::class.java)
                    }
                    onSuccess(interventionList)
                }
                else{
                    onFailure("")
                }
            }
    }

    override suspend fun fetchLineInterventions(
        companyId: String,
        lineId: String,
        onSuccess: (List<ProgressiveMaintenanceCard>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(lineId)
            .collection(FirebaseCollections.INTERVENTIONS)
            .addSnapshotListener { pmcQuerySnapshot, error ->
                if (error!=null)
                {
                    onFailure(error.message?:"Repository: failed to fetch line interventions")
                    return@addSnapshotListener
                }
                if (pmcQuerySnapshot!=null && !pmcQuerySnapshot.isEmpty)
                {
                    val interventionList=pmcQuerySnapshot.mapNotNull { pmc->
                        pmc.toObject(ProgressiveMaintenanceCard::class.java)
                    }
                    onSuccess(interventionList)
                }
                else{
                    onFailure("")
                }
            }
    }

    override suspend fun fetchPmCardById(
        companyId: String,
        productionLineId: String,
        interventionId: String,
        onSuccess: (pmc: ProgressiveMaintenanceCard) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(productionLineId)
            .collection(FirebaseCollections.INTERVENTIONS)
            .document(interventionId)
            .get()
            .addOnSuccessListener {pmcDocument->
                if (pmcDocument!=null && pmcDocument.exists())
                {
                    val pmc=pmcDocument.toObject(ProgressiveMaintenanceCard::class.java)
                    pmc?.let { onSuccess(it) }
                }
                else{onFailure("Repository: PMC not found")}
            }
            .addOnFailureListener { e->
                onFailure(e.message?:"Repository: Failed to fetch PMC")
            }
    }

    override suspend fun updatePmCard(
        pmc:ProgressiveMaintenanceCard,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(pmc.companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(pmc.productionLineId)
            .collection(FirebaseCollections.INTERVENTIONS)
            .document(pmc.interventionId)
            .set(pmc)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e-> onFailure(e.message?:"Repository: Failed to update PMC") }
    }

    override suspend fun saveCenterLineForm(
        companyId: String,
        lineId: String,
        equipmentId: String,
        clForm: CenterLineForm,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
       val docRef= Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(lineId)
            .collection(FirebaseCollections.CENTERLINES)
            .document()
        val updatedForm=clForm.copy(clFormId = docRef.id, equipmentId = equipmentId)
        docRef.set(updatedForm)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e->
                onFailure(e.message?:"Repository:Failed to upload cl")
            }
    }

    override suspend fun fetchAllCenterLines(
        companyId: String,
        lineId: String,
        onSuccess: (List<CenterLineForm>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(lineId)
            .collection(FirebaseCollections.CENTERLINES)
            .addSnapshotListener { centerLineQuerySnapshot, error ->
                if (error!=null)
                {
                    onFailure(error.message?:"Repository: failed to fetch CL")
                    return@addSnapshotListener
                }

                if (centerLineQuerySnapshot != null && !centerLineQuerySnapshot.isEmpty)
                {
                 val clList= centerLineQuerySnapshot.documents.mapNotNull { clSnapshot->
                      clSnapshot.toObject(CenterLineForm::class.java)
                  }
                    onSuccess(clList)
                }


            }
    }

    override suspend fun fetchCenterLineById(
        companyId: String,
        lineId: String,
        clId: String,
        onSuccess: (cl: CenterLineForm) -> Unit,
        onFailure: (String) -> Unit,
    )
    {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(lineId)
            .collection(FirebaseCollections.CENTERLINES)
            .document(clId)
            .addSnapshotListener { clDocSnapshot, error ->
                if (error!=null)
                {
                    onFailure(error.message?:"Repository: failed to fetch cl")
                    return@addSnapshotListener
                }
                if (clDocSnapshot!=null&&clDocSnapshot.exists())
                {
                   val cl= clDocSnapshot.toObject(CenterLineForm::class.java)
                    cl?.let { clf->onSuccess(clf) }
                }
            }
    }

    override suspend fun updateCl(
        companyId: String,
        lineId: String,
        clId: String,
        cl:CenterLineForm,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(lineId)
            .collection(FirebaseCollections.CENTERLINES)
            .document(clId)
            .set(cl)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e->
                onFailure(e.message?:"Repository:Failed to update cl")
            }
    }

    override suspend fun uploadProcedure(
        companyId: String,
        lineId: String,
        procedure: Procedure,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
       val docRef= Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(lineId)
            .collection(FirebaseCollections.PROCEDURES)
            .document()
        val updatedProcedure=procedure.copy(procedureId = docRef.id)
        docRef.set(updatedProcedure)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e-> onFailure(e.message?:"Repository:Failed to upload procedure") }
    }

    override suspend fun fetchAllEquipmentProcedures(
        companyId: String,
        lineId: String,
        equipmentId: String,
        onSuccess: (List<Procedure>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.firestore
            .collection(FirebaseCollections.COMPANIES)
            .document(companyId)
            .collection(FirebaseCollections.PRODUCTION_LINES)
            .document(lineId)
            .collection(FirebaseCollections.PROCEDURES)
            .addSnapshotListener { procedureQuerySnapshot, error ->
                if (error!=null)
                {
                    onFailure(error.message?:"Repository:Failed to fetch procedures")
                    return@addSnapshotListener
                }
                if (procedureQuerySnapshot!=null && !procedureQuerySnapshot.isEmpty)
                {
                    val procedureList=procedureQuerySnapshot.documents.mapNotNull { procedureDocumentSnapshot->
                        procedureDocumentSnapshot.toObject(Procedure::class.java)
                    }
                    val equipmentProcedureList=procedureList.filter { procedure-> procedure.equipmentId==equipmentId }
                    onSuccess(equipmentProcedureList)
                }
            }
    }
}