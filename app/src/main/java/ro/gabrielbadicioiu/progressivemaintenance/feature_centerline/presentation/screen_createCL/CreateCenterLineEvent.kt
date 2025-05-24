package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_createCL

import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineParameter

sealed class CreateCenterLineEvent {
    data object OnNavigateHome:CreateCenterLineEvent()
    data object OnAddParameterClick:CreateCenterLineEvent()
    data object OnSaveClick:CreateCenterLineEvent()

    data class OnGetArgumentData(
        val companyId:String,
        val userId:String,
        val lineId:String,
        val equipmentId:String,
        val isCreatingNewCl:Boolean):CreateCenterLineEvent()
    data class OnClNameChange(val clName:String):CreateCenterLineEvent()
    data class OnParameterNameChange(val parameterName:String, val index:Int):CreateCenterLineEvent()
    data class OnParameterValueChange(val parameterValue:String, val index:Int):CreateCenterLineEvent()
    data class OnParameterDelete(val index:Int):CreateCenterLineEvent()

}