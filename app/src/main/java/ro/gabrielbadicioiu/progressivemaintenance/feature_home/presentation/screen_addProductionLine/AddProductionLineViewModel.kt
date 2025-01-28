package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine

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
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine.AddProductionLineScreenUseCases


class AddProductionLineViewModel(
    private val useCases: AddProductionLineScreenUseCases
):ViewModel() {
    private val db=Firebase.firestore
    //states
    private val _productionLine = mutableStateOf(ProductionLine())
    val productionLine:State<ProductionLine> = _productionLine

    private val _isError= mutableStateOf(false)
    val isError:State<Boolean> = _isError

    //one time events
 private val _eventFlow = MutableSharedFlow<AddEquipmentUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class AddEquipmentUiEvent{
        data object OnExitScreen:AddEquipmentUiEvent()
        data class ToastMessage(val message:String):AddEquipmentUiEvent()
    }

    fun onEvent(event: AddProductionLineEvent)
    {
        when(event)
        {

            is AddProductionLineEvent.OnExitScreen->{
                onExitScreen()
            }
            is AddProductionLineEvent.OnProductionLineNameChange->{
                _productionLine.value=_productionLine.value.copy(lineName = useCases.onProductionLineNameChange.execute(event.name))
            }
            is AddProductionLineEvent.OnAddEquipmentClick->{
                _productionLine.value=useCases.onAddEquipmentClick.execute(productionLine=productionLine.value)
            }
            is AddProductionLineEvent.OnEquipmentNameChange->{
                _productionLine.value=
                    useCases.onEquipmentNameChange.execute(
                        newName = event.name,
                        index = event.index,
                        productionLine=_productionLine.value)
            }
            is AddProductionLineEvent.OnEquipmentDelete->{
                _productionLine.value=useCases.onEquipmentDelete.execute(productionLine = _productionLine.value, index = event.index)
            }
            is AddProductionLineEvent.OnDoneBtnClick->{
              viewModelScope.launch {
                  useCases.onDoneBtnClick.execute(
                      db = db,
                      collection = FirebaseCollections.PRODUCTION_LINES,
                      productionLine = productionLine.value,
                      onSuccess = {onExitScreen()},
                      onFailure = {e->
                          _isError.value=true
                          viewModelScope.launch { _eventFlow.emit(AddEquipmentUiEvent.ToastMessage(e)) }
                      },
                      onInvalidName = {e->
                          _isError.value=true
                          viewModelScope.launch { _eventFlow.emit(AddEquipmentUiEvent.ToastMessage(e)) }}
                  )
              }
            }
        }
    }
    private fun onExitScreen()
    {
        _productionLine.value=_productionLine.value.copy(equipments = mutableListOf(Equipment("")), lineName = "")
        viewModelScope.launch {
            _eventFlow.emit(AddEquipmentUiEvent.OnExitScreen)
        }
        _isError.value=false
    }
}