package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService

class SignIn(
    private val accountService: AccountService
) {
    suspend fun execute(
        email:String,
        password:String,
        onSuccess:(Boolean?)->Unit,
        onError:(String?)->Unit)
    {
        accountService.signIn(
            email=email,
            password=password,
            onSuccess = onSuccess,
            onError = onError)
    }
}