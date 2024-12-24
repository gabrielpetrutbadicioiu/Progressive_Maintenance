package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn

import com.google.firebase.auth.FirebaseUser


class SendVerificationEmail {

    fun execute(
        currentUser:FirebaseUser?,
        onSuccess:()->Unit,
        onFailure:(String?)->Unit
    )
    {
        currentUser?.sendEmailVerification()?.addOnCompleteListener{
            task->
            if(task.isSuccessful)
            {
                onSuccess()
            }
            else {
                onFailure(task.exception?.message)
            }
        }
    }
}