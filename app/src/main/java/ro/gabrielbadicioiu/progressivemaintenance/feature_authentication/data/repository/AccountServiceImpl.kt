package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository


import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService


class AccountServiceImpl:AccountService {
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