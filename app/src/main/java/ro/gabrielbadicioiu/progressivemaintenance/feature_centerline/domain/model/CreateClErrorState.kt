package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model

data class CreateClErrorState(
    val isFetchDataErr:Boolean=false,
    val isClNameErr:Boolean=false,
    val isParameterErr:Boolean=false,
    val errMsg:String="",
    )
