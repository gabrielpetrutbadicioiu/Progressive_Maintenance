package ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.GetUserInCompany

data class MembersScreenUseCases(
    val getUserInCompany: GetUserInCompany,
    val getAllUsersInCompany:FetchUsersInCompany,
    val onChangeUserRank: OnChangeUserRank,
    val onUpdateUserPosition: OnUpdateUserPosition,
    val onBanConfirm: OnBanConfirm
)
