package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine

data class EditProdLineUseCases(
    val loadProdLine: LoadProdLine,
    val onEquipmentEdit: OnEquipmentEdit,
    val onDeleteEditEquipment: OnDeleteEditEquipment,
    val onDoneEdit: OnDoneEdit,
    val onDeleteEditProdLine: OnDeleteEditProdLine
)
