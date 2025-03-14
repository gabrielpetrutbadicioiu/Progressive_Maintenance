package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login

import com.google.firebase.auth.FirebaseUser
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService

class OnSignInClick(
    private val accountService: AccountService,
   // private val companiesRepository: CompaniesRepository
) {
    suspend fun execute(email:String,
                        pass:String,
                        onSuccess:(FirebaseUser?) -> Unit,
                        onUnverifiedEmailErr:(FirebaseUser?)->Unit,
                        onError: (String) -> Unit){

       accountService.login(
           email = email,
           password = pass,
           onSuccess ={isEmailVerified, currentUser->

                   if (isEmailVerified!=null && isEmailVerified)
                   {
                       onSuccess(currentUser)
                   }
                   else{
                       onUnverifiedEmailErr(currentUser)
                   }

              } ,
           onFailure = {e-> onError(e)}
       )

    }
}