package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.data_source.UserDao
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.UserRepository

class UserRepositoryImpl(
    private val dao: UserDao
):UserRepository {
    override suspend fun upsertUser(user: User) {
dao.upsertUser(user = user)
    }
    override suspend fun getUserById(id: Int): User?{
        return dao.getUserById(id = id)
    }
}