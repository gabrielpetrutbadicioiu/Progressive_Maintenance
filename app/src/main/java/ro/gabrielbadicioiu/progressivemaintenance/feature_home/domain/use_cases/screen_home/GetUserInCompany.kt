package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class GetUserInCompany(
    private val repository: CompaniesRepository
) {
    suspend fun execute(
        userId:String,
        companyId:String,
        onSuccess:(UserDetails)->Unit,
        onFailure:(String)->Unit,
    )
    {
        repository.getUserInCompany(
            currentUserID = userId,
            companyID = companyId,
            onSuccess = {user->
                if (user!=null)
                {
                    onSuccess(user)
                }
                else{
                    onFailure("User is null")
                }
            },
            onFailure = {e->
                onFailure(e)
            },
            onUserNotFound = {
                onFailure("User not found")
            }
        )
    }
}