package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_CreateOwnerEmail

class OnValidateEmailFormat {

    fun execute(
        email:String,
        onSuccess:()->Unit,
        onError:()->Unit
    )
    {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            onSuccess()
        }
        else{ onError()}
    }
}