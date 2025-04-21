package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CloudStorageRepository

class CloudStorageRepositoryImpl:CloudStorageRepository {
    override suspend fun putFile(
        imageName: String,
        folderName:String,
        imageReference: StorageReference,
        localUri: Uri?,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,

    ) {
        try {
            localUri?.let { uri->
               val uploadTask= imageReference.child("$folderName/$imageName").putFile(uri).await()
                val downloadUrl= uploadTask.metadata?.reference?.downloadUrl?.await()
                onSuccess(downloadUrl.toString())
            } ?:onFailure("Error uploading image: localUri is null")
        }
        catch (e:FirebaseException)
        {
            onFailure("Firebase storage error: ${e.message}")
        }
    }

    override suspend fun deleteFile(
        imageName: String,
        folderName: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.storage.reference
            .child("$folderName/$imageName")
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e->
                onFailure(e.message?:"Repository:Couldn't delete photo")
            }
    }
}
