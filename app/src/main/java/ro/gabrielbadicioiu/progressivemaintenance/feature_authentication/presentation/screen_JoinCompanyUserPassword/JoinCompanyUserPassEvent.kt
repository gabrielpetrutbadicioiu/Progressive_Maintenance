package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserPassword

sealed class JoinCompanyUserPassEvent {

    data class OnPasswordChange(val pass:String):JoinCompanyUserPassEvent()
    data class OnConfPassChange(val confPass:String):JoinCompanyUserPassEvent()
    data class OnContinueBtnClick(val email:String, val pass:String, val hasPoppedBackstack:Boolean):JoinCompanyUserPassEvent()

    data object OnShowHidePassClick:JoinCompanyUserPassEvent()
    data object OnNavigateUp:JoinCompanyUserPassEvent()
    data object OnShowHideConfPassClick:JoinCompanyUserPassEvent()
}