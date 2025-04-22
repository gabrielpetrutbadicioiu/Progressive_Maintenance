package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository


import com.google.firebase.FirebaseException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService


class AccountServiceImpl:AccountService {
    override suspend fun login(
        email: String,
        password: String,
        onSuccess: (Boolean?, FirebaseUser?) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            onSuccess(Firebase.auth.currentUser?.isEmailVerified, Firebase.auth.currentUser)
            }
         catch (e:FirebaseAuthException){
            onFailure(e.message.toString())
        }
        catch (e:Exception){
            onFailure(e.message.toString())
        }

    }

    override suspend fun sendVerificationEmail(
        currentUser: FirebaseUser?,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        try {
            if (currentUser==null)
            {
                onFailure("Invalid user!")
                return
            }
            else{
                currentUser.sendEmailVerification()
                onSuccess("An verification email has been sent. Check your email address")
            }

        }catch (e:FirebaseException)
        {
            onFailure(e.message.toString())
        }
        catch (e:Exception){
            onFailure(e.message.toString())
        }

    }

    override suspend fun signIn(email: String,
                                password: String,
                                onSuccess: (Boolean?, FirebaseUser?) -> Unit,
                                onError: (String?) -> Unit
    ){
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            task->
            if (task.isSuccessful)
            {
                onSuccess(Firebase.auth.currentUser?.isEmailVerified, Firebase.auth.currentUser)
            }
            else{
                onError(task.exception?.message)
            }
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        onSuccess:(currentUser:FirebaseUser?)->Unit,
        onError:(String?)->Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            task->
            if(task.isSuccessful)
            {
                    val user=Firebase.auth.currentUser
                    onSuccess(user)
            }
            else{
                onError(task.exception?.message)
            }
        }

     }

    override suspend fun reAuthenticate(
        userId: String,
        oldPass: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val currentUser=Firebase.auth.currentUser
        if (currentUser?.uid==null)
        {
            onFailure("Current user is null")
            return
        }
        if (currentUser.uid!=userId)
        {
            onFailure("ID doesn't match")
            return
        }
        val credential= EmailAuthProvider.getCredential(currentUser.email.toString(), oldPass )
        currentUser.reauthenticate(credential)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e-> onFailure(e.message?:"Repository: Failed to authenticate") }
    }

    override suspend fun updatePassword(
        newPass: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        Firebase.auth.currentUser
            ?.updatePassword(newPass)
            ?.addOnSuccessListener {onSuccess()  }
            ?.addOnFailureListener { e-> onFailure(e.message?:"Repository:Failed to change password") }

    }

    override suspend fun logOut() {

        Firebase.auth.signOut()
    }
}