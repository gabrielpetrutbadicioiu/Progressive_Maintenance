package ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails

data class MemberStatus(
    val user:UserDetails=UserDetails(),
    val showDropDown:Boolean=false
)
