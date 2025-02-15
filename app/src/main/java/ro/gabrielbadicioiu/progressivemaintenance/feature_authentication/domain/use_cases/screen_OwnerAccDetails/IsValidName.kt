package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OwnerAccDetails

class IsValidName {

    fun execute(name:String,
                onSuccess:(String)->Unit,
                onErr:(String, String)->Unit)
    {
        val trimmedName=name.trim()
        val nameRegex= "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]{2,}$".toRegex()
        if (trimmedName.matches(nameRegex))
        {
            onSuccess(trimmedName)
        }
        else{
           //onErr()
        }
    }
}