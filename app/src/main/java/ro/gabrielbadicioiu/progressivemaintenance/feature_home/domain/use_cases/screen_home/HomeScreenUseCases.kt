package ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home

data class HomeScreenUseCases(
    val fetchProductionLines: FetchProductionLines,
    val onExpandBtnClick: OnExpandBtnClick,
    val getUserById: GetUserInCompany,
    val onProductionLineListener: OnProductionLineListener
)
