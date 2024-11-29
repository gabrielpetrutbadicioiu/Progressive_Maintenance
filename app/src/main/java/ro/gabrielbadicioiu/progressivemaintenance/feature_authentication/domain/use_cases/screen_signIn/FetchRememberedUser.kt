package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.UserRepository

class FetchRememberedUser(
    private val repository: UserRepository
) {
    suspend fun execute():User
    {
        return repository.getUserById(0)
    }
}