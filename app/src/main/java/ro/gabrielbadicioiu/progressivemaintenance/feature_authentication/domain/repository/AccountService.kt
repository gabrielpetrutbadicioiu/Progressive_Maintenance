package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository

import com.google.firebase.auth.FirebaseUser


interface AccountService {

    suspend fun signIn(
        email:String,
        password:String,
        onSuccess: (Boolean?, FirebaseUser?) -> Unit,
        onError: (String?) -> Unit)
    suspend fun signUp(
        email:String,
        password:String,
        firstName:String,
        lastName:String,
        onSuccess:()->Unit,
        onError:(String?)->Unit)

}