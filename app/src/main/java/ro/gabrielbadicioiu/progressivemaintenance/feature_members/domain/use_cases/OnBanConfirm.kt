package ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion.MemberStatus

class OnBanConfirm(
    private val repository: CompaniesRepository
) {
    suspend fun execute(
        companyId:String,
        user:MemberStatus,
        isBanned:Boolean,
        onFailure:(String)->Unit,
        onSuccess:()->Unit
                        )
    {//asta ii schimba daca ii da ban sau nu
        val banedUser=user.user.copy(hasBeenBanned = isBanned)
        repository.updateUser(
            companyId = companyId,
            user = banedUser,
            onFailure = {e-> onFailure(e)},
            onSuccess = {onSuccess()}
        )
    }
}