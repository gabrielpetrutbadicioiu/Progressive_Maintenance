package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OwnerAccDetails

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class OnAddUserToCompany(
    private val repository: CompaniesRepository
) {
    suspend fun execute(
        user:UserDetails,
        onFirstNameFail:()->Unit,
        onLastNameFail:()->Unit,
        companyID:String,
        onSuccess:()->Unit,
        onFailure:(String)->Unit
        )
    {
        //verificat nume
        val trimmedFirstName=user.firstName.trim()
        val trimmedLastName=user.lastName.trim()
        val trimmedPosition= user.position.trim()
        val nameRegex= "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]{2,}$".toRegex()
        if (!trimmedFirstName.matches(nameRegex))
        {
            onFirstNameFail()
            return
        }
        if (!trimmedLastName.matches(nameRegex))
        {
            onLastNameFail()
            return
        }
        val newUser=user.copy(
            firstName = trimmedFirstName,
            lastName = trimmedLastName,
            position = trimmedPosition,
            )
        try {
            repository.addUserToCompany(
                companyID=companyID,
                user =newUser,
                onSuccess = {onSuccess()},
                onFailure = {e-> onFailure(e)}
            )
        }
        catch (e:Exception)
        {
            onFailure("Failed to add user ${e.message.toString()}")
        }

    }
}