package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository


import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User


interface UserRepository {

    suspend fun upsertUser(user: User)

    suspend fun getUserById(id:Int): User
}