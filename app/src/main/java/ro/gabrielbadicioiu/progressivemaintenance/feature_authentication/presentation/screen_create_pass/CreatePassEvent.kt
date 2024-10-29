package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass

 sealed class CreatePassEvent
 {
     data class PassInput(val value:String):CreatePassEvent()
     data class ConfirmPassInput(val value:String):CreatePassEvent()
    data object OnShowPassClick:CreatePassEvent()
     data object OnBackBtnClick:CreatePassEvent()
     data object OnContinueBtnClick:CreatePassEvent()

}