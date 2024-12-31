package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {
    //states
    var selectedItemIndex by mutableStateOf(0)
        private set
private val _isSearching = MutableStateFlow(false)
    val isSearching=_isSearching.asStateFlow()
//one time events
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class UiEvent()
    {
        data object OnFabClick: UiEvent()
    }

    fun onEvent(event: HomeScreenEvent)
    {
        when(event)
        {
            is HomeScreenEvent.OnAddProductionLineClick ->{
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.OnFabClick)
                }

            }
        }
    }

}