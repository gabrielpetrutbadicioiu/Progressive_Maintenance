package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model

import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank

data class UserDetails(
    val firstName:String="",
    val lastName:String="",
    val position:String="",
    val rank:String=UserRank.USER.name,
    val email:String="",
    val profilePicture:String="",
    val userID:String="",
    val companyID:String="",
    val hasBeenBanned:Boolean=false
){
    fun toFirebaseDocument():HashMap<String, Any>
    {
        return hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "position" to position,
            "rank" to rank,
            "email" to email,
            "profilePicture" to profilePicture,
            "userID" to userID,
            "companyID" to companyID,
            "hasBeenBanned" to hasBeenBanned
        )
    }
}
