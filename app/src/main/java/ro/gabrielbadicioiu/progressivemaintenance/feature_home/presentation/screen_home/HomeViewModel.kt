package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.HomeScreenUseCases

class HomeViewModel(
    private val useCases: HomeScreenUseCases
):ViewModel() {
    //states
    private val _companyID= mutableStateOf("")
    val companyID:State<String> = _companyID

    private val _userID= mutableStateOf("")
    val userID:State<String> = _userID

    private val _userDetails= mutableStateOf(UserDetails())
    val userDetails:State<UserDetails> = _userDetails

    private val _getUserErr= mutableStateOf(false)
    val getUserErr:State<Boolean> = _getUserErr//todo

    private val _getUserErrMsg= mutableStateOf("")
    val getUserErrMsg:State<String> = _getUserErrMsg
   private val  _productionLineList= mutableStateOf<List<ProductionLine>>(emptyList())
    val productionLineList:State<List<ProductionLine>> = _productionLineList

    private val _showProgressBar= mutableStateOf(false)
    val showProgressBar:State<Boolean> = _showProgressBar

    private val _failedToFetchProdLineErrMsg= mutableStateOf("")
    val failedToFetchProdLinesErrMsg:State<String> = _failedToFetchProdLineErrMsg

    private val _fetchProdLineErr= mutableStateOf(false)
    val fetchProdLineErr:State<Boolean> = _fetchProdLineErr

//one time events
    private val _eventFlow = MutableSharedFlow<HomeScreenUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class HomeScreenUiEvent()
    {
        data object OnAddProductionLineClick: HomeScreenUiEvent()
        data object OnMembersScreenClick:HomeScreenUiEvent()
        data class ToastMessage(val message:String):HomeScreenUiEvent()
        data class OnEditBtnClick(val id:String):HomeScreenUiEvent()
        data class OnNavigateTo(val screen:Screens):HomeScreenUiEvent()
    }

    fun onEvent(event: HomeScreenEvent)
    {
        when(event)
        {
            is HomeScreenEvent.OnFetchArgumentData->{
                _userID.value=event.userID
                _companyID.value=event.companyID
            }

            is HomeScreenEvent.OnFetchProductionLines->{
                _showProgressBar.value=true
                viewModelScope.launch {
                    useCases.fetchProductionLines.execute(
                        onResult = {productionLines ->
                            _productionLineList.value=productionLines
                            _fetchProdLineErr.value=false
                            _failedToFetchProdLineErrMsg.value=""
                                   },
                        onFailure = {e->
                            _fetchProdLineErr.value=true
                            _failedToFetchProdLineErrMsg.value=e
                            _productionLineList.value= emptyList()
                        },
                        companyID =_companyID.value
                    )
                }
            }
            is HomeScreenEvent.OnAddProductionLineClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(HomeScreenUiEvent.OnAddProductionLineClick)
                }
            }
            is HomeScreenEvent.OnExpandBtnClick->{
                _productionLineList.value=useCases.onExpandBtnClick.execute(
                    productionLinesList = _productionLineList.value,
                    id = event.id)
            }
            is HomeScreenEvent.OnEditBtnClick->{
                viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.OnEditBtnClick(id = event.id)) }
            }
            is HomeScreenEvent.OnNavigateUp->{
                viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.OnNavigateTo(Screens.SignInScreen)) }
            }
            is HomeScreenEvent.OnGetUserById->{
                viewModelScope.launch {
                    useCases.getUserById.execute(
                        userId = _userID.value,
                        companyId = _companyID.value,
                        onFailure = {e->
                            _getUserErr.value=true
                            _getUserErrMsg.value=e

                        },
                        onSuccess = {user->
                            _getUserErr.value=false
                            _getUserErrMsg.value=""
                            _userDetails.value=user.copy()
                        }
                    )
                }
            }
            is HomeScreenEvent.OnProductionLineListener->{
                viewModelScope.launch {
                    useCases.onProductionLineListener.execute(
                        companyId = _companyID.value,
                        onProductionLineAdded = {addedLineId ->
                            //todo
                            onEvent(HomeScreenEvent.OnFetchProductionLines)
                        },
                        onProductionLineUpdated = {updatedLineId ->  },//todo
                        onProductionLineRemoved = {removedLineId ->  },//todo
                        onFailure = {e->}//todo
                    )
                }
            }
            is HomeScreenEvent.OnMembersScreenClick->{
                viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.OnMembersScreenClick) }
            }

        }
    }

}