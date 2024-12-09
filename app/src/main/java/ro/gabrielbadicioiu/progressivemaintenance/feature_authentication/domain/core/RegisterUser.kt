package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.core

data class RegisterUser(
    var firstName:String="",
    var lastName:String="",
    var email:String="",
    var password:String=""
)
