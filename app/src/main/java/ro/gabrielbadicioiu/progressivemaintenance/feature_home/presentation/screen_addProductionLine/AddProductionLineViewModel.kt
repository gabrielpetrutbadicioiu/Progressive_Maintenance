package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
   var productionLine by mutableStateOf(ProductionLine())
        private set
    //one time events
 private val _eventFlow = MutableSharedFlow<AddEquipmentUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class AddEquipmentUiEvent{
        data object OnExitScreen:AddEquipmentUiEvent()
    }

    fun onEvent(event: AddProductionLineEvent)
    {
        when(event)
        {

            is AddProductionLineEvent.OnExitScreen->{
                exitScreen()
            }
            is AddProductionLineEvent.OnProductionLineNameChange->{
                productionLine=productionLine.copy(lineName = useCases.onProductionLineNameChange.execute(event.name))
            }
            is AddProductionLineEvent.OnAddEquipmentClick->{
                productionLine=useCases.onAddEquipmentClick.execute(productionLine=productionLine)
            }
            is AddProductionLineEvent.OnEquipmentNameChange->{
                productionLine=
                    useCases.onEquipmentNameChange.execute(
                        newName = event.name,
                        index = event.index,
                        productionLine=productionLine)
            }
            is AddProductionLineEvent.OnEquipmentDelete->{
                productionLine=useCases.onEquipmentDelete.execute(productionLine = productionLine, index = event.index)
            }
        }

    }
    private fun exitScreen()
    {
        productionLine=productionLine.copy(equipments = mutableListOf(Equipment("")), lineName = "")
        viewModelScope.launch {
            _eventFlow.emit(AddEquipmentUiEvent.OnExitScreen)
        }
    }
}