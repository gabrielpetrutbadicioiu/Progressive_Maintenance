package ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases


import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion.MemberStatus

class OnUpdateUserPosition
    (private val repository: CompaniesRepository){
        suspend fun execute(
            companyId:String,
            user:MemberStatus,
            onSuccess:()->Unit,
            onFailure:(String)->Unit
        )
        {
            repository.updateUser(
                companyId = companyId,
                user = user.user,
                onSuccess = {onSuccess()},
                onFailure = {e->onFailure(e)}
            )
        }
}