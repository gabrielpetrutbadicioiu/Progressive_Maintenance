package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model

import com.google.firebase.firestore.Exclude

data class Equipment(
    val name:String="",
    val id:String="",
    @get:Exclude
    val isExpanded:Boolean=false

)
