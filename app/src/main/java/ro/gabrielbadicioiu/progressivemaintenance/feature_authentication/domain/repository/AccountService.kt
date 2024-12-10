package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.AuthResult

interface AccountService {

    suspend fun signIn(
        email:String,
        password:String,
        onSuccess: (Boolean?) -> Unit,
        onError: (String?) -> Unit)
    suspend fun signUp(
        email:String,
        password:String,
        firstName:String,
        lastName:String,
        onSuccess:()->Unit,
        onError:(String?)->Unit)

}