package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.presentation.screen_displayAllIPMCards

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.model.DisplayInterventionArgumentData
import ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.model.SortOption
import ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.use_cases.DisplayInterventionsUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

class DisplayAllPmCardsViewModel(
    private val companiesRepository: CompaniesRepository,
    private val useCases: DisplayInterventionsUseCases
):ViewModel() {
    private val _company= mutableStateOf(Company())
    val company:State<Company> = _company


    //states
    private val _displayInterventionArgumentData= mutableStateOf(DisplayInterventionArgumentData())

    private val _pmCardList= mutableStateOf <List<ProgressiveMaintenanceCard>>(emptyList())
    val pmCardList:State<List<ProgressiveMaintenanceCard>> = _pmCardList

    private val _isErr= mutableStateOf(false)
    val isErr:State<Boolean> = _isErr
    private val _fetchInterventionsErr= mutableStateOf("")
    val fetchInterventionsErr:State<String> = _fetchInterventionsErr

    private val _query = mutableStateOf("")
    val query:State<String> = _query

    private val _sortOption= mutableStateOf(SortOption())
    val sortOption:State<SortOption> = _sortOption
    //one time events
    private val _eventFlow= MutableSharedFlow<DisplayAllPmCardsUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    private val _originalList= mutableStateOf<List<ProgressiveMaintenanceCard>> (emptyList())

    sealed class DisplayAllPmCardsUiEvent{
        data object OnNavigateHome:DisplayAllPmCardsUiEvent()
        data class OnShowToast(val message:String):DisplayAllPmCardsUiEvent()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: DisplayAllPmCardsScreenEvent)
    {
        when(event)
        {
            is DisplayAllPmCardsScreenEvent.OnNavigateHome->{
                viewModelScope.launch { _eventFlow.emit(DisplayAllPmCardsUiEvent.OnNavigateHome) }
            }
            is DisplayAllPmCardsScreenEvent.OnGetArgumentData->{

                _displayInterventionArgumentData.value=_displayInterventionArgumentData.value.copy(
                 displayAllInterventions=event.displayAllInterventions,
                 displayLineInterventions=event.displayLineInterventions,
                 displayEquipmentInterventions=event.displayEquipmentInterventions,
                 companyId=event.companyId,
                 userId=event.userId,
                 lineId=event.lineId,
                 equipmentId=event.equipmentId,
                )
                viewModelScope.launch {
                    companiesRepository.getCompanyById(
                        companyId = _displayInterventionArgumentData.value.companyId,
                        onSuccess = {comp-> _company.value=comp.copy()},
                        onFailure = {e->
                            viewModelScope.launch { _eventFlow.emit(DisplayAllPmCardsUiEvent.OnShowToast(e))} }
                            )
                }
            }
            is DisplayAllPmCardsScreenEvent.OnGetAllPmCards->{
                viewModelScope.launch {
                    viewModelScope.launch {
                        try {
                            useCases.fetchInterventions.execute(
                                displayAllInterventions = _displayInterventionArgumentData.value.displayAllInterventions,
                                displayLineInterventions = _displayInterventionArgumentData.value.displayLineInterventions,
                                displayEquipmentInterventions = _displayInterventionArgumentData.value.displayEquipmentInterventions,
                                companyId = _displayInterventionArgumentData.value.companyId,
                                onResult = {interventions->
                                    _pmCardList.value=interventions
                                    _originalList.value=interventions
                                    onSort()
                                    _isErr.value=false
                                           },
                                onFailure = {e->
                                    _isErr.value=true
                                    _fetchInterventionsErr.value=e
                                }
                            )
                        }catch (e:Exception)
                        {
                            _isErr.value=true
                            _fetchInterventionsErr.value=e.message?:"ViewModel:Failed to fetch interventions"
                        }
                    }
                }
            }
            is DisplayAllPmCardsScreenEvent.OnQueryChange->{
                _query.value=event.query
                    if (event.query.isEmpty())
                    {
                        _pmCardList.value=_originalList.value
                        onSort()
                    }
                else{
                        _pmCardList.value=useCases.onSearchIntervention.execute(query = event.query, pmCardList = _pmCardList.value.toList())
                        onSort()
                    }

            }
            is DisplayAllPmCardsScreenEvent.OnExpandInterventionClick->{
                val mutablePmList=_pmCardList.value.toMutableList()
                val modifiedPmc=_pmCardList.value[event.index].copy(isExpanded = !_pmCardList.value[event.index].isExpanded)
                mutablePmList[event.index] = modifiedPmc
                _pmCardList.value=mutablePmList
            }
            is DisplayAllPmCardsScreenEvent.OnSortInterventionsByDate->{
                _sortOption.value=_sortOption.value.copy(sortByDate = true, sortByDuration = false, resolvedOnly = false, unresolvedOnly = false)
                _pmCardList.value=useCases.sortInterventionsByDate.execute(_pmCardList.value.toList()).toList()
            }
            is DisplayAllPmCardsScreenEvent.OnSortInterventionsByDuration->{
                _sortOption.value=_sortOption.value.copy(sortByDate = false, sortByDuration = true, resolvedOnly = false, unresolvedOnly = false)
                _pmCardList.value=useCases.sortInterventionsByDuration.execute(_pmCardList.value.toList()).toList()
            }
            is DisplayAllPmCardsScreenEvent.OnSortUnresolvedFirst->{
                _sortOption.value=_sortOption.value.copy(sortByDate = false, sortByDuration = false, resolvedOnly = false, unresolvedOnly = true)
                _pmCardList.value=useCases.sortUnresolvedFirst.execute(_pmCardList.value.toList()).toList()
            }
            is DisplayAllPmCardsScreenEvent.OnSortResolvedFirst->{
                _sortOption.value=_sortOption.value.copy(sortByDate = false, sortByDuration = false, resolvedOnly = true, unresolvedOnly = false)
                _pmCardList.value=useCases.sortResolvedFirst.execute(_pmCardList.value.toList()).toList()
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onSort()
    {
        if (_sortOption.value.sortByDate)
        {
            onEvent(DisplayAllPmCardsScreenEvent.OnSortInterventionsByDate)
        }
        if (_sortOption.value.sortByDuration)
        {
            onEvent(DisplayAllPmCardsScreenEvent.OnSortInterventionsByDuration)
        }
        if (_sortOption.value.resolvedOnly)
        {
            onEvent(DisplayAllPmCardsScreenEvent.OnSortResolvedFirst)
        }
        if (_sortOption.value.unresolvedOnly)
        {
            onEvent(DisplayAllPmCardsScreenEvent.OnSortUnresolvedFirst)
        }
    }
}
