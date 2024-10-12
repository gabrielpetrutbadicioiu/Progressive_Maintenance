package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass

 sealed class CreatePassEvent
 {
     data class PassInput(val value:String):CreatePassEvent()
     data class ConfirmPassInput(val value:String):CreatePassEvent()
    object OnShowPassClick:CreatePassEvent()

}