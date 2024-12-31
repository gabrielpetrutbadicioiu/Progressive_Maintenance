package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addEquipment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class AddEquipmentViewModel:ViewModel() {
    //states
    var equipmentNr by mutableStateOf(0)
        private set
    //one time events
 private val _eventFlow = MutableSharedFlow<AddEquipmentUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class AddEquipmentUiEvent{

    }

    fun OnEvent(event: AddEquipmentEvent)
    {
        when(event)
        {
            is AddEquipmentEvent.OnAddEquipmentClick->{
                equipmentNr++
            }
        }

    }
}