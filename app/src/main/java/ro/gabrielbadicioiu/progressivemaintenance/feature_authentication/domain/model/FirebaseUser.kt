package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model

data class FirebaseUser(
    val firstName:String="",
    val lastName:String="",
    val position:String="",
    val rank:String="",
    val company: Company= Company()
)
