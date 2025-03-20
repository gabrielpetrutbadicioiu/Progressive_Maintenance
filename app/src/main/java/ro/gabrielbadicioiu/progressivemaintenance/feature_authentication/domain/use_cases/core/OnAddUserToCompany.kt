package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core

import android.net.Uri
import com.google.firebase.storage.StorageReference
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CloudStorageRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class OnAddUserToCompany(
    private val companiesRepository: CompaniesRepository,
    private val cloudStorageRepository: CloudStorageRepository
) {
    suspend fun execute(
        user:UserDetails,
        onFirstNameFail:()->Unit,
        onLastNameFail:()->Unit,
        companyID:String,
        onSuccess:()->Unit,
        onFailure:(String)->Unit,
        localUri: Uri?,
        imageName:String,
        imageFolderName:String,
        imageReference: StorageReference,
        )
    {

        //verificat nume
        val trimmedFirstName=user.firstName.trim()
        val trimmedLastName=user.lastName.trim()
        val trimmedPosition= user.position.trim()
        val nameRegex= "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]{2,}$".toRegex()
        if (!trimmedFirstName.matches(nameRegex))
        {
            onFirstNameFail()
            return
        }
        if (!trimmedLastName.matches(nameRegex))
        {
            onLastNameFail()
            return
        }
        var newUser=user.copy(
            firstName = trimmedFirstName,
            lastName = trimmedLastName,
            position = trimmedPosition,
            companyID = companyID
            )
        try {
            cloudStorageRepository.putFile(
                localUri = localUri,
                folderName = imageFolderName,
                imageReference = imageReference,
                imageName = imageName,
                onSuccess = {cloudUri->
                    newUser=newUser.copy(profilePicture = cloudUri)
                },
                onFailure = {e-> onFailure(e)}
            )
            companiesRepository.addUserToCompany(
                companyID=companyID,
                user =newUser,
                onSuccess = {
                    onSuccess()},
                onFailure = {e-> onFailure(e)}
            )

        }
        catch (e:Exception)
        {
            onFailure("Failed to add user ${e.message.toString()}")
        }

    }
}