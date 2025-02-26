package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_userName

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService

class SignUp(
    private val accountService: AccountService
) {
    suspend fun execute(email:String,
                        password:String,
                        firstName:String,
                        lastName:String,
                        onSuccess:()->Unit,
                        onError:(String?)->Unit)
    {
//        accountService.signUp(
//            email = email,
//            password = password,
//            onSuccess = onSuccess,
//            onError = onError)
    }
}