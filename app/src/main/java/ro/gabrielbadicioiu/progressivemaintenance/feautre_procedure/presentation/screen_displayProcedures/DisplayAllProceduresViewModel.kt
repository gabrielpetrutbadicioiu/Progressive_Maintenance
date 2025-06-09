package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_displayProcedures

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure

class DisplayAllProceduresViewModel(
    private val companiesRepository: CompaniesRepository
):ViewModel()
{
    //states
    private val _proceduresList=mutableStateOf<List<Procedure>> (emptyList())
    val proceduresList:State<List<Procedure>> = _proceduresList

    private val _uiState= mutableStateOf(DisplayProceduresUiState())
    val uiState:State<DisplayProceduresUiState> = _uiState

    private val _searchQuery= mutableStateOf("")
    val searchQuery:State<String> = _searchQuery

    //one time events
    private val _eventFlow= MutableSharedFlow<DisplayAllProceduresUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class DisplayAllProceduresUiEvent{

        data class OnProcedureClick(val procedureId:String):DisplayAllProceduresUiEvent()

        data object OnNavigateHome:DisplayAllProceduresUiEvent()
    }
    //vars
    private var args=DisplayAllProceduresArgs()
    private var originalProcedureList= emptyList<Procedure>()


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

                //fetch procedures
                viewModelScope.launch {
                    try {
                        companiesRepository.fetchAllEquipmentProcedures(
                            companyId = event.companyId,
                            lineId = event.lineId,
                            equipmentId = event.equipmentId,
                            onSuccess = {procedures ->
                                _proceduresList.value=procedures
                                originalProcedureList=procedures
                                _uiState.value=DisplayProceduresUiState()},
                            onFailure = {e-> _uiState.value=_uiState.value.copy(isFetchProceduresErr = true, errMsg = e) }
                        )
                    } catch (e:Exception)
                    {
                        _uiState.value=_uiState.value.copy(isFetchProceduresErr = true, errMsg = e.message?:"ViewModel: Failed to fetch procedures")
                    }
                }
            }
            is DisplayAllProceduresScreenEvent.OnNavigateHome->{
                viewModelScope.launch { _eventFlow.emit(DisplayAllProceduresUiEvent.OnNavigateHome) }
            }
            is DisplayAllProceduresScreenEvent.OnSearchQueryChange->{
                _searchQuery.value=event.query
                if (_searchQuery.value.isEmpty())
                {
                    _proceduresList.value=originalProcedureList
                }else
                {
                    _proceduresList.value=_proceduresList.value.filter { procedure-> procedure.procedureName.contains(event.query) }
                }
            }
            is DisplayAllProceduresScreenEvent.OnProcedureClick->{
                viewModelScope.launch { _eventFlow.emit(DisplayAllProceduresUiEvent.OnProcedureClick(procedureId =event.procedureId )) }
            }

        }
    }
}