package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import ro.gabrielbadicioiu.progressivemaintenance.core.CloudStorageFolders
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CloudStorageRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

class OnInterventionUriResult(
    private val cloudStorageRepository: CloudStorageRepository
){
  suspend  fun execute(
        pmCard: ProgressiveMaintenanceCard,
        photoName:String,
        uri:Uri,
        companyName:String,
        imageRef:StorageReference,
        onFailure:(String)->Unit,
        ):ProgressiveMaintenanceCard
    {
        var updatedPmCard=pmCard.copy()
        val compName="$companyName interventions"
        if (pmCard.photo1.isEmpty())
        {
            cloudStorageRepository.putFile(
                imageName = photoName,
                folderName = compName,
                imageReference = imageRef,
                localUri = uri,
                onSuccess = {downloadUrl->
                    updatedPmCard=updatedPmCard.copy(photo1 = downloadUrl, photo1Name = photoName)
                },
                onFailure = {e-> onFailure(e)}
            )
            return updatedPmCard
         }
        if (pmCard.photo2.isEmpty())
        {
            cloudStorageRepository.putFile(
                imageName = photoName,
                folderName = compName,
                imageReference = imageRef,
                localUri = uri,
                onSuccess = {downloadUrl->
                    updatedPmCard=updatedPmCard.copy(photo2 = downloadUrl, photo2Name = photoName)
                },
                onFailure = {e-> onFailure(e)}
            )
            return updatedPmCard
        }
        if (pmCard.photo3.isEmpty())
        {
            cloudStorageRepository.putFile(
                imageName = photoName,
                folderName = compName,
                imageReference = imageRef,
                localUri = uri,
                onSuccess = {downloadUrl->
                    updatedPmCard=updatedPmCard.copy(photo3 = downloadUrl, photo3Name = photoName)
                },
                onFailure = {e-> onFailure(e)}
            )
            return updatedPmCard
        }
        return updatedPmCard
    }
}