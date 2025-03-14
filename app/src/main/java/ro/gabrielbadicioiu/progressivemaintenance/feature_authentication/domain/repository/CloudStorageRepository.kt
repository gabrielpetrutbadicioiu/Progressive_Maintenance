package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository

import android.net.Uri
import com.google.firebase.storage.StorageReference

interface CloudStorageRepository
{
    suspend fun putFile(imageName:String,
                        folderName:String,
                        imageReference: StorageReference,
                        localUri: Uri?,
                        onSuccess:(String)->Unit,
                        onFailure:(String)->Unit)


}