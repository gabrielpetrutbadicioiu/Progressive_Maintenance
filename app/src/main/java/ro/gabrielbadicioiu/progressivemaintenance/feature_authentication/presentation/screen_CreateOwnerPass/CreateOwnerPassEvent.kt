package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CreateOwnerPass

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company

sealed class CreateOwnerPassEvent {

    data class OnPassChange(val pass:String):CreateOwnerPassEvent()
    data class OnConfPassChange(val confPass:String):CreateOwnerPassEvent()
    data class OnContinueBtnClick(val email:String, val pass: String, val company: Company):CreateOwnerPassEvent()

    data object OnNavigateUp:CreateOwnerPassEvent()
    data object OnShowPassClick:CreateOwnerPassEvent()
    data object OnShowConfPassClick:CreateOwnerPassEvent()
}