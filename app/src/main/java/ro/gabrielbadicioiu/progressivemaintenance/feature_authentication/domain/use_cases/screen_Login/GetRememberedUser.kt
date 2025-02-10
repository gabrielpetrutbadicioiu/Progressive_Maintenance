package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.UserRepository

class GetRememberedUser(
    private val repository: UserRepository
) {
    suspend fun execute():User {
        return repository.getUserById(0) ?: User()
    }
}