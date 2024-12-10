package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.AuthResult

class AccountServiceImpl:AccountService {
    override suspend fun signIn(email: String,
                                password: String,
                                onSuccess: (Boolean?) -> Unit,
                                onError: (String?) -> Unit
    ){

        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            task->
            if (task.isSuccessful)
            {

                onSuccess(Firebase.auth.currentUser?.isEmailVerified)

            }
            else{
                onError(task.exception?.message)
            }
        }
//        return try{
//            Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{}
//            AuthResult(isError = false, errorMessage = "")
//        }
//        catch (e:Exception)
//        {
//            when(e)
//            {
//                is FirebaseAuthInvalidUserException -> AuthResult(isError = true, errorMessage = "Incorrect account or password. Try again.")
//                is FirebaseAuthInvalidCredentialsException -> AuthResult(isError = true, errorMessage = "Incorrect account or password. Try again.")
//                else -> AuthResult(isError = true, errorMessage = "${e.message}")
//            }
//        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
        firstName:String,
        lastName:String,
        onSuccess:()->Unit,
        onError:(String?)->Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            task->
            if(task.isSuccessful)
            {
                val user=Firebase.auth.currentUser
                user?.updateProfile(
                    userProfileChangeRequest {
                        displayName= "$firstName $lastName"
                    }
                )
                onSuccess()
            }
            else{
                onError(task.exception?.message)
            }
        }

     }

}