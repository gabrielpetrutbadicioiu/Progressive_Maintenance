package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.AuthResult

interface AccountService {

    suspend fun signIn(email:String, password:String):AuthResult
    suspend fun signUp(email:String, password:String)

}