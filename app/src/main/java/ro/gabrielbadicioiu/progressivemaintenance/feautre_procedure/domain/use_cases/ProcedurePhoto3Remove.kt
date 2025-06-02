package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CloudStorageRepository
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure


class ProcedurePhoto3Remove(
    private val cloudStorageRepository: CloudStorageRepository
){
    suspend fun execute(
        procedure: Procedure,
        index:Int,
        companyName:String,
        onSuccess:(Procedure)->Unit,
        onFailure:(String)->Unit
    )
    {val updatedStep=procedure.steps[index].copy(photo3Uri = "")
        val updatedStepList=procedure.steps.toMutableList()
        updatedStepList[index]=updatedStep.copy()
        cloudStorageRepository.deleteFile(
            imageName = "${procedure.procedureName}_photo3$index",
            folderName = "$companyName procedures",
            onSuccess = {
                onSuccess(procedure.copy(steps = updatedStepList.toList()))
            },
            onFailure={e-> onFailure(e)}
        )
    }
}