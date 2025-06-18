package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_createProcedure

sealed class CreateProcedureEvent {
    data object OnNavigateHome:CreateProcedureEvent()
    data object OnSaveBtnClick:CreateProcedureEvent()
    data object OnCancelBtnClick:CreateProcedureEvent()
    data object OnAddNewStep:CreateProcedureEvent()

    data class OnFetchArgumentData(
        val companyId:String,
        val userId:String,
        val productionLineId: String,
        val equipmentId: String ):CreateProcedureEvent()
    data class OnProcedureNameChange(val procedureName:String):CreateProcedureEvent()
    data class OnStepDescrChange(val description:String, val index:Int):CreateProcedureEvent()
    data class OnStepPhotoUriResult(val localUri:String, val index:Int):CreateProcedureEvent()
    data class OnPhoto1Delete(val index:Int):CreateProcedureEvent()
    data class OnPhoto2Delete(val index:Int):CreateProcedureEvent()
    data class OnPhoto3Delete(val index: Int):CreateProcedureEvent()
    data class OnStepDelete(val index:Int):CreateProcedureEvent()

}