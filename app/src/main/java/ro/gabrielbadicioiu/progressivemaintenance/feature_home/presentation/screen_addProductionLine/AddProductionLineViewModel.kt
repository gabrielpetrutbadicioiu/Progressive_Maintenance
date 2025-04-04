package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine.AddProductionLineScreenUseCases


class AddProductionLineViewModel(
    private val useCases: AddProductionLineScreenUseCases
):ViewModel() {

    //states
    private val _productionLine = mutableStateOf(ProductionLine())
    val productionLine:State<ProductionLine> = _productionLine

    private val _isError= mutableStateOf(false)
    val isError:State<Boolean> = _isError

    private val _companyID = mutableStateOf("")
    private val _currentUserId= mutableStateOf("")
    private val _showProgressBar= mutableStateOf(false)
     val showProgressBar:State<Boolean> = _showProgressBar

    //one time events
 private val _eventFlow = MutableSharedFlow<AddEquipmentUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class AddEquipmentUiEvent{
        data object OnExitScreen:AddEquipmentUiEvent()
        data object OnNavigateUp:AddEquipmentUiEvent()
        data class ToastMessage(val message:String):AddEquipmentUiEvent()

    }

    fun onEvent(event: AddProductionLineEvent)
    {
        when(event)
        {


            is AddProductionLineEvent.OnProductionLineNameChange->{
                _productionLine.value=_productionLine.value.copy(lineName = event.name.replaceFirstChar { char-> char.uppercase() })
            }
            is AddProductionLineEvent.OnAddEquipmentClick->{
                _productionLine.value=useCases.onAddEquipmentClick.execute(productionLine=productionLine.value)
            }
            is AddProductionLineEvent.OnEquipmentNameChange->{
                val equipmentList= mutableListOf<Equipment>()
                _productionLine.value.equipments.forEachIndexed { index, equipment ->
                    if (index==event.index) equipmentList.add(Equipment(name = event.name.replaceFirstChar { char-> char.uppercase() })) else equipmentList.add(equipment)
                }
                _productionLine.value=_productionLine.value.copy(equipments = equipmentList)
            }
            is AddProductionLineEvent.OnEquipmentDelete->{
                _productionLine.value=useCases.onEquipmentDelete.execute(productionLine = _productionLine.value, index = event.index)
            }
            is AddProductionLineEvent.OnDoneBtnClick->{
              viewModelScope.launch {
                  useCases.onDoneBtnClick.execute(

                      productionLine = productionLine.value,
                      onSuccess = {
                          _showProgressBar.value=true
                          _isError.value=false
                          _productionLine.value=_productionLine.value.copy(equipments = mutableListOf(Equipment("")), lineName = "")
                          viewModelScope.launch { _eventFlow.emit(AddEquipmentUiEvent.OnNavigateUp) }},
                      onFailure = {e->
                          _showProgressBar.value=false
                          _isError.value=true
                          viewModelScope.launch { _eventFlow.emit(AddEquipmentUiEvent.ToastMessage(e)) }
                      },
                      onInvalidName = {e->
                          _isError.value=true
                          viewModelScope.launch { _eventFlow.emit(AddEquipmentUiEvent.ToastMessage(e)) }},
                      companyID =_companyID.value
                  )
              }
            }
            is AddProductionLineEvent.OnNavigateUp->{
                _isError.value=false
                viewModelScope.launch { _eventFlow.emit(AddEquipmentUiEvent.OnNavigateUp)}
            }
            is AddProductionLineEvent.OnGetArgumentData->{
                _companyID.value=event.companyID
                _currentUserId.value=event.currentUserId
                _productionLine.value=_productionLine.value.copy(addedModifiedByUserId = event.currentUserId)
                _showProgressBar.value=false
            }
        }
    }

}