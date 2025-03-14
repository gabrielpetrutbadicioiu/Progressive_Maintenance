package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.UserRepository

class RememberUser(
    private val repository: UserRepository
){
    suspend fun execute(email:String,
                        pass:String,
                        isRemembered:Boolean,
                        employer:String,
                        employerId:String)
    {
        var user=User(email=email,
            password = pass,
            userID = 0,
            rememberMe = isRemembered,
            companyName = employer,
            companyID = employerId
            )
        if (!isRemembered)
        {
            user=User()
        }
        repository.upsertUser(user = user)
    }
}