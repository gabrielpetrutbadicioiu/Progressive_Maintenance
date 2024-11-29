package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.data_source

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: User)

    @Query("SELECT * FROM `remembered-user` WHERE userID=:id")
    suspend fun getUserById(id:Int):User
}