package ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion

import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank

sealed class MembersScreenEvent {

    data object OnActionBtnClick:MembersScreenEvent()
    data object OnHomeBtnClick:MembersScreenEvent()
    data object OnFetchAllUsersInCompany:MembersScreenEvent()
    data object OnDismissDropdownMenu:MembersScreenEvent()
    data object OnDismissEditPositionAlertDialog:MembersScreenEvent()
    data object OnUpdatePositionConfirm:MembersScreenEvent()
    data object OnDismissKickDialog:MembersScreenEvent()

    data class OnFetchArgumentData(val companyId:String, val userId:String):MembersScreenEvent()
    data class OnShowDropdownMenu(val user:MemberStatus):MembersScreenEvent()
    data class OnChangeUserRank(val user: MemberStatus, val rank: UserRank):MembersScreenEvent()
    data class OnEditPositionClick(val user: MemberStatus):MembersScreenEvent()
    data class OnPositionValueChange(val newPosition:String):MembersScreenEvent()
    data class OnKickClick(val tappedUser:MemberStatus):MembersScreenEvent()

}