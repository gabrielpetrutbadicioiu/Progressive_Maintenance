package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine

data class AddProductionLineScreenUseCases(
    val onProductionLineNameChange: OnProductionLineNameChange,
    val onAddEquipmentClick: OnAddEquipmentClick,
    val onEquipmentNameChange: OnEquipmentNameChange,
    val onEquipmentDelete: OnEquipmentDelete,
    val onDoneBtnClick:OnDoneBtnClick
)
