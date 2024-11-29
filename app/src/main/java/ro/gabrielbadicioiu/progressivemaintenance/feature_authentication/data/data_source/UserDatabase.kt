package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase:RoomDatabase() {
    abstract val dao:UserDao
}