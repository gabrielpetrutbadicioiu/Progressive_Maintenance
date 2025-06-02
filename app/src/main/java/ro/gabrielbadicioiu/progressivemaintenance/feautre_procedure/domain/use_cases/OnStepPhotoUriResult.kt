package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.use_cases

import androidx.core.net.toUri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CloudStorageRepository
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure

class OnStepPhotoUriResult(
    private val cloudStorageRepository: CloudStorageRepository
) {
    suspend fun execute(
        procedure: Procedure,
        index:Int,
        companyName:String,
        localUri:String,
        onSuccess:(procedure:Procedure)->Unit,
        onFailure:(String)->Unit
    ){
        val updatedStepsList=procedure.steps.toMutableList()
        var updatedStep=updatedStepsList[index].copy()
        if (procedure.steps[index].photo1Uri.isEmpty())
        {
            cloudStorageRepository.putFile(
                imageName = "${procedure.procedureName}_photo1$index",
                folderName ="$companyName procedures",
                imageReference = Firebase.storage.reference,
                localUri = localUri.toUri(),
                onSuccess = {globalUri->
                 updatedStep=updatedStep.copy(photo1Uri = globalUri)
                    updatedStepsList[index]= updatedStep.copy()
                    onSuccess(procedure.copy(steps = updatedStepsList.toList()))
                },
                onFailure = {e-> onFailure(e)}
            )
            return
        }
        if (procedure.steps[index].photo2Uri.isEmpty())
        {
            cloudStorageRepository.putFile(
                imageName = "${procedure.procedureName}_photo2$index",
                folderName ="$companyName procedures",
                imageReference = Firebase.storage.reference,
                localUri = localUri.toUri(),
                onSuccess = {globalUri->
                    updatedStep=updatedStep.copy(photo2Uri = globalUri)
                    updatedStepsList[index]= updatedStep.copy()
                    onSuccess(procedure.copy(steps = updatedStepsList.toList()))
                },
                onFailure = {e-> onFailure(e)}
            )
            return
        }
        if (procedure.steps[index].photo3Uri.isEmpty())
        {
            cloudStorageRepository.putFile(
                imageName = "${procedure.procedureName}_photo3$index",
                folderName ="$companyName procedures",
                imageReference = Firebase.storage.reference,
                localUri = localUri.toUri(),
                onSuccess = {globalUri->
                    updatedStep=updatedStep.copy(photo3Uri = globalUri)
                    updatedStepsList[index]= updatedStep.copy()
                    onSuccess(procedure.copy(steps = updatedStepsList.toList()))
                },
                onFailure = {e-> onFailure(e)}
            )
            return
        }
    }
}