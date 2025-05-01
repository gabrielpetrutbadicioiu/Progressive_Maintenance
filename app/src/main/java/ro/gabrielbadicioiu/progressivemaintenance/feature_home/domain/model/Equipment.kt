package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model

import com.google.firebase.firestore.Exclude

data class Equipment(
    val name:String="",
    @get:Exclude
    val isExpanded:Boolean=false

)
