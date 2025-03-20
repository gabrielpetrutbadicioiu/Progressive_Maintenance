package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_companyDetails

import android.net.Uri
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CloudStorageRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class OnRegisterCompany(
    private val companiesRepository: CompaniesRepository,
    private val cloudStorageRepository: CloudStorageRepository
) {

    suspend fun execute(
        company: Company,
        onSuccess:(String)->Unit,
        onFailure:(String)->Unit,
        collectionReference: CollectionReference,
        localUri: Uri?,
        imageName:String,
        imageFolderName:String,
        imageReference: StorageReference,
    )
    {
        var newCompany=company
        cloudStorageRepository.putFile(
            localUri = localUri,
            folderName = imageFolderName,
            imageReference = imageReference,
            imageName = imageName,
            onSuccess = {cloudUri->newCompany=newCompany.copy(companyLogoUrl = cloudUri)},
            onFailure = {e-> onFailure(e)}
        )

        companiesRepository.registerCompany(
            collectionReference = collectionReference,
            company = newCompany,
            onSuccess = {documentID->
                onSuccess(documentID)},
            onFailure = {e-> onFailure(e)}
            )

    }
}