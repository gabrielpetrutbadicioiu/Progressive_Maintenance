package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.data_source.UserDao
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User

class RememberMe(
    private val dao: UserDao
){
    suspend fun execute(
        email:String,
        pass:String,
        isRememberMeActive:Boolean
    ):User
    {
        return User()
//    val user=User(
//        email = email,
//        password = pass,
//        rememberMe = isRememberMeActive,
//        userID = 0)
//        dao.upsertUser(user = user)
//        return dao.getUserById(0)
    }
}