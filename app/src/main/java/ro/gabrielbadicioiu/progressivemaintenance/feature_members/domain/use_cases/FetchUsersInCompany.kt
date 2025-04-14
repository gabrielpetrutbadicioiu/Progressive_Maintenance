package ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class FetchUsersInCompany(
    private val repository: CompaniesRepository
) {
    suspend fun execute(
        companyId:String,
        onResult:(List<UserDetails>)->Unit,
        onFailure:(String)->Unit
    )
    {
        repository.fetchAllUsersInCompany(
            companyID = companyId,
            onResult = {userList-> onResult(userList)},
            onFailure = {e-> onFailure(e)}
        )
    }
}