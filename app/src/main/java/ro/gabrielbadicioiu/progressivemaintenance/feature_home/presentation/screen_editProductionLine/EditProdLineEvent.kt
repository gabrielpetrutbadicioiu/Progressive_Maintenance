package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine

sealed class EditProdLineEvent {
    data class OnFetchEditedLine(val userId:String, val companyId:String, val prodLineId:String):EditProdLineEvent()
    data class OnProdLineNameChange(val name:String):EditProdLineEvent()
    data class OnEquipmentNameChange(val name:String, val index:Int):EditProdLineEvent()
    data class OnDeleteEditEquipment(val index: Int):EditProdLineEvent()
    data object OnNavigateBack:EditProdLineEvent()
    data object OnUpdateProdLine:EditProdLineEvent()
    data object OnAddEquipment:EditProdLineEvent()
    data object OnDeleteClick:EditProdLineEvent()
    data object OnAlertDialogDismiss:EditProdLineEvent()
    data object OnDeleteDialogConfirm:EditProdLineEvent()



}