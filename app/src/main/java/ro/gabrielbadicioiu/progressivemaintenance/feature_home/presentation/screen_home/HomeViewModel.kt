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
init {
    viewModelScope.launch {
        useCases.fetchProductionLines.execute(
            db = db,
            collection = FirebaseCollections.PRODUCTION_LINES,
            onSuccess = {result-> _productionLineList.value=result},
            onFailure = {e-> viewModelScope.launch { _eventFlow.emit(HomeScreenUiEvent.ToastMessage(e)) } })
    }
}
    fun onEvent(event: HomeScreenEvent)
    {
        when(event)
        {
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

        }
    }

}