package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_displayProcedures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DisplayAllProceduresViewModel:ViewModel()
{
    //states

    //one time events
    private val _eventFlow= MutableSharedFlow<DisplayAllProceduresUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class DisplayAllProceduresUiEvent{
    data object OnNavigateHome:DisplayAllProceduresUiEvent()
    }
    //vars
    private var args=DisplayAllProceduresArgs()



    fun onEvent(event: DisplayAllProceduresScreenEvent)
    {
        when(event)
        {
            is DisplayAllProceduresScreenEvent.OnGetArgumentData->{
                args=args.copy(
                    companyId = event.companyId,
                    lineId = event.lineId,
                    equipmentId = event.equipmentId,
                    userId = event.userId)
            }
            is DisplayAllProceduresScreenEvent.OnNavigateHome->{
                viewModelScope.launch { _eventFlow.emit(DisplayAllProceduresUiEvent.OnNavigateHome) }
            }
        }
    }
}