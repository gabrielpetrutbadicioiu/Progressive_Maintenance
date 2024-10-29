package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_userName

sealed class UserNameEvent {
    data class FirstNameInput(val value:String):UserNameEvent()
    data class LastNameInput(val value: String):UserNameEvent()
    data object OnBackBtnClick:UserNameEvent()
    data object OnFinishBtnClick:UserNameEvent()
}