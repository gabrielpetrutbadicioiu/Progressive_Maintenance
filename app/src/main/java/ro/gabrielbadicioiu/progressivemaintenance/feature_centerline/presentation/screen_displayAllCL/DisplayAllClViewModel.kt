package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_displayAllCL

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm

class DisplayAllClViewModel(
    private val companiesRepository: CompaniesRepository
):ViewModel()
{
    //states
    private val _args= mutableStateOf(DisplayAllClArgs())

    private val _equipmentClList= mutableStateOf<List<CenterLineForm>>(emptyList())
    val equipmentClList:State<List<CenterLineForm>> = _equipmentClList

    private val _errState= mutableStateOf(DisplayAllClErrState())
    val errState:State<DisplayAllClErrState> = _errState

    private val _query = mutableStateOf("")
    val query:State<String> = _query

    private var originalList= emptyList<CenterLineForm>()
    //one time events
    private val _eventFlow= MutableSharedFlow<DisplayAllClUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class DisplayAllClUiEvent{
        data object OnNavigateHome:DisplayAllClUiEvent()

        data class OnClClick(val clId:String):DisplayAllClUiEvent()
    }

    fun onEvent(event:DisplayAllClScreenEvent)
    {
        when(event)
        {
            is DisplayAllClScreenEvent.OnGetArgumentData->{
                _args.value=_args.value.copy(
                    companyId = event.companyId,
                    userId = event.userId,
                    productionLineId = event.productionLineId,
                    equipmentId = event.equipmentId
                )
                //fetch cl list
                viewModelScope.launch {
                    try {
                        companiesRepository.fetchAllCenterLines(
                            companyId = event.companyId,
                            lineId = event.productionLineId,
                            onSuccess = {clList-> _equipmentClList.value=clList.filter { cl-> cl.equipmentId==event.equipmentId }
                                        originalList=_equipmentClList.value
                                        },
                            onFailure = {e-> _errState.value=_errState.value.copy(isFetchClErr = true, errMsg = e)}
                        )
                    }catch (e:Exception)
                    {
                        _errState.value=_errState.value.copy(isFetchClErr = true, errMsg = e.message?:"ViewModel:Failed to fetch CL list")
                    }
                }
            }
            is DisplayAllClScreenEvent.OnNavigateHome->{
                viewModelScope.launch { _eventFlow.emit(DisplayAllClUiEvent.OnNavigateHome) }
            }
            is DisplayAllClScreenEvent.OnClClick->{
                viewModelScope.launch { _eventFlow.emit(DisplayAllClUiEvent.OnClClick(clId = _equipmentClList.value[event.clIndex].clFormId)) }
            }
            is DisplayAllClScreenEvent.OnQueryChange->{
                _query.value=event.query
                if (_query.value.isEmpty())
                {
                    _equipmentClList.value=originalList
                }
                _equipmentClList.value=_equipmentClList.value.filter { cl-> cl.clName.contains(event.query, ignoreCase = true) }
            }

        }
    }
}