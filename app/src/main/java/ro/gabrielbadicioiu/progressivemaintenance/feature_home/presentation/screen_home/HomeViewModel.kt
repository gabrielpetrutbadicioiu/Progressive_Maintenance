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
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
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

    private val _clickedProdLine= mutableStateOf(ProductionLine())
    val clickedProdLine:State<ProductionLine> = _clickedProdLine

    private val _clickedEq =  mutableStateOf(Equipment())
    val clickedEq:State<Equipment> = _clickedEq

    private var equipmentLine= Equipment()

    private var _clickedEquipmentIndex=0

//one time events
    private val _eventFlow = MutableSharedFlow<HomeScreenUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class HomeScreenUiEvent
    {
        data object OnAddProductionLineClick: HomeScreenUiEvent()
        data object OnMembersScreenClick:HomeScreenUiEvent()
        data object OnNavigateToProfile:HomeScreenUiEvent()
        data object OnNavigateToLogInterventionScreen:HomeScreenUiEvent()
        data object OnNavigateToDisplayGlobalInterventionsScreen:HomeScreenUiEvent()

        data class ToastMessage(val message:String):HomeScreenUiEvent()
        data class OnEditBtnClick(val id:String):HomeScreenUiEvent()
        data class OnNavigateTo(val screen:Screens):HomeScreenUiEvent()
        data class OnNavigateToLineInterventionsScreen(val productionLine: ProductionLine):HomeScreenUiEvent()
        data class OnNavigateToEquipmentInterventionsScreen(val equipment: Equipment, val line:ProductionLine):HomeScreenUiEvent()
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
                            if (_userDetails.value.hasBeenBanned)
                            {
                                viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.OnNavigateTo(Screens.BannedScreen)) }

                            }

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

            is HomeScreenEvent.OnProfileClick->{
                viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.OnNavigateToProfile) }
            }
            is HomeScreenEvent.OnEquipmentClick->{
                    _clickedEquipmentIndex=event.productionLineIndex
                _clickedProdLine.value=_productionLineList.value[event.productionLineIndex]
                _clickedEq.value=event.equipment.copy()
               _productionLineList.value=useCases.onShowDropDownMenu.execute(
                   productionLineList = _productionLineList.value,
                   lineIndex = event.productionLineIndex,
                   clickedEquipment = event.equipment
               )
            }
            is HomeScreenEvent.OnEquipmentDropdownMenuDismiss->{
                _productionLineList.value=useCases.onHideDropDownMenu
                    .execute(prodLineList = _productionLineList.value,
                        clickedLine = clickedProdLine.value,
                        clickedLineIndex = _clickedEquipmentIndex)
            }
            is HomeScreenEvent.OnLogInterventionClick->{
                viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.OnNavigateToLogInterventionScreen) }
                onEvent(HomeScreenEvent.OnEquipmentDropdownMenuDismiss)
            }
            is HomeScreenEvent.OnSearchInterventionsClick->{
                viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.OnNavigateToDisplayGlobalInterventionsScreen) }
            }
            is HomeScreenEvent.OnDismissLineDropDown->{
                val updatedList=_productionLineList.value.toMutableList()
                updatedList[event.index] = _productionLineList.value[event.index].copy(showDropDown = false)
                _productionLineList.value=updatedList
            }
            is HomeScreenEvent.OnShowLineDropDown->{
                val updatedList=_productionLineList.value.toMutableList()
                updatedList[event.index] = _productionLineList.value[event.index].copy(showDropDown = true)
                _productionLineList.value=updatedList
            }
            is HomeScreenEvent.OnShowLineInterventionsClick->{
                viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.OnNavigateToLineInterventionsScreen(_productionLineList.value[event.index])) }
                onEvent(HomeScreenEvent.OnDismissLineDropDown(event.index))
            }
            is HomeScreenEvent.OnViewEquipmentInterventionsClick->{

                viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.OnNavigateToEquipmentInterventionsScreen(equipment = _clickedEq.value.copy(), line = _clickedProdLine.value.copy())) }
                onEvent(HomeScreenEvent.OnEquipmentDropdownMenuDismiss)
            }

        }
    }

}