package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService

class SignUp(
    private val accountService: AccountService
) {
    suspend fun execute(email:String, password:String)
    {
        accountService.signUp(email, password)
    }
}