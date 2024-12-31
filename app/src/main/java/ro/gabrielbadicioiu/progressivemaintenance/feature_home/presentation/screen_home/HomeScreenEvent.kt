package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home

sealed class HomeScreenEvent {
    data object OnAddProductionLineClick: HomeScreenEvent()
}