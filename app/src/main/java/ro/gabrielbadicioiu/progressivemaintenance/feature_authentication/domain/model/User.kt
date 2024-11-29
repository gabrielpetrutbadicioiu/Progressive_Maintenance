package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remembered-user")
data class User(
    @ColumnInfo(name = "user-email")
    val email:String="",
    @ColumnInfo(name="user-pass")
    val password:String="",
    @ColumnInfo(name = "remembered")
    val rememberMe:Boolean=false,
    @PrimaryKey(autoGenerate = false)
    val userID:Int=0

)
