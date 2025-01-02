package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addEquipment

sealed class AddEquipmentEvent {
    data object OnAddEquipmentClick:AddEquipmentEvent()
    data object OnExitScreen:AddEquipmentEvent()
    data class OnProductionLineNameChange(val name:String):AddEquipmentEvent()
}