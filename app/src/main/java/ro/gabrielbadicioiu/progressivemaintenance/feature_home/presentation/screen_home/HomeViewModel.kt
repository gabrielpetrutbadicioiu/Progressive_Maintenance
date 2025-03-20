package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseCollections
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.HomeScreenUseCases

class HomeViewModel(
    private val useCases: HomeScreenUseCases
):ViewModel() {
    private val db=Firebase.firestore
    //states
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
        data object OnFabClick: HomeScreenUiEvent()
        data class ToastMessage(val message:String):HomeScreenUiEvent()
        data class OnEditBtnClick(val id:String):HomeScreenUiEvent()
        data class OnNavigateTo(val screen:Screens):HomeScreenUiEvent()
    }

    fun onEvent(event: HomeScreenEvent)
    {
        when(event)
        {
            is HomeScreenEvent.OnFetchProductionLines->{
                _showProgressBar.value=true
                viewModelScope.launch {
                    useCases.fetchProductionLines.execute(
                        db = db,
                        collection = FirebaseCollections.PRODUCTION_LINES,
                        onSuccess = {result->
                            _showProgressBar.value=false
                            _fetchProdLineErr.value=false
                            _failedToFetchProdLineErrMsg.value=""
                            _productionLineList.value=result},
                        onFailure = {e->
                            _showProgressBar.value=false
                            _fetchProdLineErr.value=true
                            _failedToFetchProdLineErrMsg.value=e
                            viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.ToastMessage(e)) } })
                }
            }
            is HomeScreenEvent.OnAddProductionLineClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(HomeScreenUiEvent.OnFabClick)
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

        }
    }

}