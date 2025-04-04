package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home


sealed class HomeScreenEvent {

    data object OnFetchProductionLines:HomeScreenEvent()
    data object OnAddProductionLineClick: HomeScreenEvent()
    data object OnNavigateUp:HomeScreenEvent()
    data object OnGetUserById:HomeScreenEvent()
    data object OnProductionLineListener:HomeScreenEvent()

    data class OnExpandBtnClick(val id:String): HomeScreenEvent()
    data class OnEditBtnClick(val id:String):HomeScreenEvent()
    data class OnFetchArgumentData(val companyID:String, val userID:String):HomeScreenEvent()

}