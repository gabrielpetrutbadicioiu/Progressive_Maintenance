package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login

import com.google.firebase.auth.FirebaseUser
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService

class SendVerificationEmail(
    private val accountService: AccountService
) {
    suspend fun execute(currentUser: FirebaseUser?,
                        onSuccess:(String)->Unit,
                        onFailure:(String)->Unit)
    {
        accountService.sendVerificationEmail(
            currentUser=currentUser,
            onSuccess = {message->onSuccess(message)},
            onFailure = {e-> onFailure(e)})
    }
}