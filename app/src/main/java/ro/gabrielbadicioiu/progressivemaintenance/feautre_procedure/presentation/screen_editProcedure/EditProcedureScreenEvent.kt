package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_editProcedure


sealed class EditProcedureScreenEvent {

    data object OnNavigateToVisualizeProcedure:EditProcedureScreenEvent()
    data object OnAddNewStep: EditProcedureScreenEvent()
    data object OnCancelBtnClick:EditProcedureScreenEvent()
    data object OnSaveBtnClick:EditProcedureScreenEvent()

    data class OnFetchArgumentData(
        val companyId:String,
        val userId:String,
        val productionLineId: String,
        val equipmentId: String,
        val procedureId:String
    ):EditProcedureScreenEvent()

    data class OnProcedureNameChange(val name:String):EditProcedureScreenEvent()
    data class OnStepDescrChange(val description:String, val index:Int):EditProcedureScreenEvent()
    data class OnPhoto1Delete(val index:Int):EditProcedureScreenEvent()
    data class OnPhoto2Delete(val index:Int):EditProcedureScreenEvent()
    data class OnPhoto3Delete(val index:Int):EditProcedureScreenEvent()
    data class OnUriResult(val index:Int, val localUri:String):EditProcedureScreenEvent()
    data class OnDeleteProcedureStep(val index:Int):EditProcedureScreenEvent()
}