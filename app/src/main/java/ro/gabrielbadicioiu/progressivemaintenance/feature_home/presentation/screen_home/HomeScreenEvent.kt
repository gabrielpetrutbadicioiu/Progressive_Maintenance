package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home

sealed class HomeScreenEvent {

    data object OnAddProductionLineClick: HomeScreenEvent()
    data class OnExpandBtnClick(val id:String): HomeScreenEvent()
    data class OnEditBtnClick(val id:String):HomeScreenEvent()
}