package ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class OnChangeUserRank(
    private val repository: CompaniesRepository
) {
    suspend fun execute(promoteTo:UserRank,
                        user:UserDetails,
                        companyId:String,
                        onFailure:(String)->Unit,
                        onSuccess:()->Unit
                        )
    {
        val promotedUser=user.copy(rank = promoteTo.name)
        repository.updateUser(
            companyId = companyId,
            user = promotedUser,
            onFailure = {e-> onFailure(e)},
            onSuccess = {onSuccess()}
        )
    }
}