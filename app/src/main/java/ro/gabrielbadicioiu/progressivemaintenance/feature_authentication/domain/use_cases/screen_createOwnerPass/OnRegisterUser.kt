package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_createOwnerPass

import com.google.firebase.auth.FirebaseUser
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService

class OnRegisterUser(private val accountService: AccountService) {

    suspend fun execute(
        email:String,
        pass:String,
        onSuccess:(FirebaseUser?)->Unit,
        onFailure:(String)->Unit
    )
    {
        accountService.signUp(
            email = email,
            password = pass,
            onSuccess = {currentUser->
                onSuccess(currentUser)
            },
            onError = {e->onFailure(e?:"Unknown firebase auth error")}
        )
    }
}