package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login

import com.google.firebase.auth.FirebaseUser
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService

class OnSignInClick(
    private val accountService: AccountService
) {
    suspend fun execute(email:String,
                        pass:String,
                        onSuccess:(Boolean?, FirebaseUser?) -> Unit,
                        onError: (String) -> Unit){

       accountService.login(
           email = email,
           password = pass,
           onSuccess ={isEmailVerified, currentUSer->onSuccess(isEmailVerified, currentUSer)} ,
           onFailure = {e-> onError(e)}
       )

    }
}