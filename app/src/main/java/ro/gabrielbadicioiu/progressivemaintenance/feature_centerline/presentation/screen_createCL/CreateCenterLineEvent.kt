package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_createCL


sealed class CreateCenterLineEvent {
    data object OnNavigateHome:CreateCenterLineEvent()
    data object OnAddParameterClick:CreateCenterLineEvent()
    data object OnSaveClick:CreateCenterLineEvent()
    data object OnEditBtnClick:CreateCenterLineEvent()
    data object OnCancelEditClick:CreateCenterLineEvent()
    data object OnUpdateClClick:CreateCenterLineEvent()

    data class OnGetArgumentData(
        val companyId:String,
        val userId:String,
        val lineId:String,
        val equipmentId:String,
        val clId:String,
        val isCreatingNewCl:Boolean):CreateCenterLineEvent()
    data class OnClNameChange(val clName:String):CreateCenterLineEvent()
    data class OnParameterNameChange(val parameterName:String, val index:Int):CreateCenterLineEvent()
    data class OnParameterValueChange(val parameterValue:String, val index:Int):CreateCenterLineEvent()
    data class OnParameterDelete(val index:Int):CreateCenterLineEvent()

}