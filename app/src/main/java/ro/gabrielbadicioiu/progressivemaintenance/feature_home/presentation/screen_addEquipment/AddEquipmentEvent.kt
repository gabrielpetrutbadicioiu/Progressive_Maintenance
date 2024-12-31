package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addEquipment

sealed class AddEquipmentEvent {
    data object OnAddEquipmentClick:AddEquipmentEvent()
}