package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine

sealed class AddProductionLineEvent {
    data object OnNavigateUp:AddProductionLineEvent()
    data object OnDoneBtnClick:AddProductionLineEvent()
    data object OnAddEquipmentClick:AddProductionLineEvent()
    data class OnProductionLineNameChange(val name:String):AddProductionLineEvent()
    data class OnEquipmentNameChange(val name:String, val index:Int):AddProductionLineEvent()
    data class OnEquipmentDelete(val index:Int):AddProductionLineEvent()
    data class OnGetArgumentData(val companyID:String, val currentUserId:String):AddProductionLineEvent()

}