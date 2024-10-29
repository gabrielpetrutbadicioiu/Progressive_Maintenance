package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_userName

class NameValidation
{
    fun execute(
        name:String
    ):NameValidationResult
    {
        val regex= "^[a-zA-Z]*$".toRegex()
        if(regex.matches(name))
        {
            return NameValidationResult(isError = false, capitalizedName = name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            })
        }
        else{
            return NameValidationResult(isError = true, capitalizedName = name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            })
        }
    }
}