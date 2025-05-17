package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home

import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment


sealed class HomeScreenEvent {

    data object OnFetchProductionLines:HomeScreenEvent()
    data object OnAddProductionLineClick: HomeScreenEvent()
    data object OnNavigateUp:HomeScreenEvent()
    data object OnGetUserById:HomeScreenEvent()
    data object OnProductionLineListener:HomeScreenEvent()
    data object OnMembersScreenClick:HomeScreenEvent()
    data object OnProfileClick:HomeScreenEvent()
    data object OnEquipmentDropdownMenuDismiss:HomeScreenEvent()
    data object OnLogInterventionClick:HomeScreenEvent()
    data object OnSearchInterventionsClick:HomeScreenEvent()
    data object OnViewEquipmentInterventionsClick:HomeScreenEvent()

    data class OnExpandBtnClick(val id:String): HomeScreenEvent()
    data class OnEditBtnClick(val id:String):HomeScreenEvent()
    data class OnFetchArgumentData(val companyID:String, val userID:String):HomeScreenEvent()
    data class OnEquipmentClick(val productionLineIndex:Int, val equipment:Equipment):HomeScreenEvent()
    data class OnDismissLineDropDown(val index:Int):HomeScreenEvent()
    data class OnShowLineDropDown(val index:Int):HomeScreenEvent()
    data class OnShowLineInterventionsClick(val index: Int):HomeScreenEvent()

}