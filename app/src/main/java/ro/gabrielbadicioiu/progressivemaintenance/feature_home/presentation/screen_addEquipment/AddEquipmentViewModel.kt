package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addEquipment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class AddEquipmentViewModel:ViewModel() {
    //states
    var equipmentNr by mutableStateOf(0)
        private set
    var equipmentList = mutableListOf<String>()
        private set
    var productionLineName by mutableStateOf("")
        private set
    //one time events
 private val _eventFlow = MutableSharedFlow<AddEquipmentUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class AddEquipmentUiEvent{
        data object OnExitScreen:AddEquipmentUiEvent()
    }

    fun OnEvent(event: AddEquipmentEvent)
    {
        when(event)
        {
            is AddEquipmentEvent.OnAddEquipmentClick->{
                equipmentList.add("")
                equipmentNr=equipmentList.size
            }
            is AddEquipmentEvent.OnExitScreen->{
                viewModelScope.launch {
                    _eventFlow.emit(AddEquipmentUiEvent.OnExitScreen)
                }
            }
            is AddEquipmentEvent.OnProductionLineNameChange->{
                productionLineName=event.name
            }
        }

    }
}