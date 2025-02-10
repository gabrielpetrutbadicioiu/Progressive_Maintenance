package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_SelectCountry.SelectCountryUseCases

class SelectCountryScreenViewModel(
    private val useCases: SelectCountryUseCases
):ViewModel() {
val countriesList= countries.map {
    Category(name = it.key.toString(),
        items = it.value)
}
    //states
    private val _searchedCountry= mutableStateOf("")
    val searchedCountry:State<String> = _searchedCountry

    private val _filteredList= mutableStateOf(emptyList<String>())
    val filteredList:State<List<String>> = _filteredList

    //one time events
    private val _eventFlow= MutableSharedFlow<SelectCountryUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()
    sealed class SelectCountryUiEvent{
        data class OnExitScreen(val country:String):SelectCountryUiEvent()
    }

    fun onEvent(event: SelectCountryScreenEvent)
    {
        when(event)
        {
            is SelectCountryScreenEvent.OnCancelClick->{
                _searchedCountry.value=""
                viewModelScope.launch {_eventFlow.emit(SelectCountryUiEvent.OnExitScreen(""))}
            }
            is SelectCountryScreenEvent.OnQueryChange->{
                _searchedCountry.value=event.countryName.replaceFirstChar { char-> char.uppercase() }
                useCases.onQueryChange.execute(
                    query = _searchedCountry.value,
                    onQueryChange = {newList-> _filteredList.value=newList}, //ceva nu am facut bine la filtered list
                    countryList = countriesList)
            }
            is SelectCountryScreenEvent.OnCountryClick->{
                viewModelScope.launch { _eventFlow.emit(SelectCountryUiEvent.OnExitScreen(event.selectedCountry)) }
            }
        }
    }

}