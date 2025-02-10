package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login

import com.google.firebase.auth.FirebaseUser

class GetCurrentFirebaseUser {
    fun execute(firebaseUser: FirebaseUser?):FirebaseUser?
    {
        return firebaseUser
    }
}