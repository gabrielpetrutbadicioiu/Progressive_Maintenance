package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email.EmailValidationUseCases

sealed class EmailValidationEvent {
    data class EnteredEmail(val value:String):EmailValidationEvent()
    object OnNextClick:EmailValidationEvent()

}