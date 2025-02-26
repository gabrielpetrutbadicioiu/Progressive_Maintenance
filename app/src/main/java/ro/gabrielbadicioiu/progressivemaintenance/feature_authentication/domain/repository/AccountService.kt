package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository

import com.google.firebase.auth.FirebaseUser


interface AccountService {
suspend fun sendVerificationEmail(
    currentUser: FirebaseUser?,
    onSuccess: (String) -> Unit,
    onFailure:(String)->Unit)
    suspend fun login(email: String,
                      password: String,
                      onSuccess: (Boolean?, FirebaseUser?) -> Unit,
                      onFailure:(String)->Unit)
    suspend fun signIn(
        email:String,
        password:String,
        onSuccess: (Boolean?, FirebaseUser?) -> Unit,
        onError: (String?) -> Unit)
    suspend fun signUp(
        email:String,
        password:String,
        onSuccess:(FirebaseUser?)->Unit,
        onError:(String?)->Unit)

}