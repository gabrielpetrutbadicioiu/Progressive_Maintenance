package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation


sealed class EmailValidationEvent {
    data class EnteredEmail(val value:String):EmailValidationEvent()
    data object OnNextClick:EmailValidationEvent()
    data object OnBackBtnClick:EmailValidationEvent()
    data object OnContinueBtnClick:EmailValidationEvent()

}