package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_visualizeProcedure

import android.media.audiofx.DynamicsProcessing.Eq
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure

class VisualizeProcedureScreenViewModel(
    private val companiesRepository: CompaniesRepository
):ViewModel() {
    //states
    private val _uiErrState= mutableStateOf(VisualizeProcedureUiErrState())
    val uiErrState: State<VisualizeProcedureUiErrState> =_uiErrState

    private val _procedure= mutableStateOf(Procedure())
    val procedure:State<Procedure> = _procedure

    private val _productionLine= mutableStateOf(ProductionLine())
    val productionLine:State<ProductionLine> = _productionLine

    private val _author= mutableStateOf(UserDetails())
    val author:State<UserDetails> =_author

    private val _equipment= mutableStateOf(Equipment())
    val equipment:State<Equipment> = _equipment

    //oneTimeEvents
    private val _eventFlow= MutableSharedFlow<VisualizeProcedureUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class VisualizeProcedureUiEvent{
        data object OnNavigateToDisplayAllProceduresScreen:VisualizeProcedureUiEvent()
        data class OnNavigateToDisplayPhotoScreen(val uri:String):VisualizeProcedureUiEvent()
    }

    //vars
    private var args=VisualizeProcedureScreenArgumentData()

    fun onEvent(event:VisualizeProcedureScreenEvent)
    {
        when(event)
        {
            is VisualizeProcedureScreenEvent.OnFetchArgumentData->{
                args=args.copy(
                    userId = event.userId,
                    companyId = event.companyId,
                    productionLineId = event.productionLineId,
                    equipmentId = event.procedureId,
                    procedureId = event.userId
                )

                viewModelScope.launch {
                    try {
                        companiesRepository.fetchProcedureById(
                            companyId = event.companyId,
                            lineId = event.productionLineId,
                            procedureId = event.procedureId,
                            onSuccess = {procedure->
                                _procedure.value=procedure.copy()
                            _uiErrState.value=VisualizeProcedureUiErrState()},
                            onFailure = {e->
                                _uiErrState.value=_uiErrState.value.copy(isFetchProcedureErr = true, errMsg = e)
                            }
                        )
                    }catch (e:Exception)
                    {
                        _uiErrState.value=_uiErrState.value.copy(isFetchProcedureErr = true, errMsg = e.message?:"ViewModel: Failed to fetch procedure")
                    }

                }

                viewModelScope.launch {
                    try{
                        companiesRepository.getProductionLineById(
                            companyId = event.companyId,
                            productionLineId = event.productionLineId,
                            onSuccess = {line->
                                _uiErrState.value=_uiErrState.value.copy(isFetchProductionLineErr = false, errMsg = "")
                                _productionLine.value=line.copy()
                                _equipment.value=_productionLine.value.equipments.find { eq-> eq.id==event.equipmentId } ?:Equipment()
                            },
                            onFailure = {e-> _uiErrState.value=_uiErrState.value.copy(isFetchProductionLineErr = true, errMsg = e)}
                        )
                    }catch (e:Exception)
                    {
                        _uiErrState.value=_uiErrState.value.copy(isFetchProductionLineErr = true, errMsg = e.message?:"ViewModel:Failed to fetch production line")
                    }
                }

                viewModelScope.launch {
                    try {
                        companiesRepository.getUserInCompany(
                            currentUserID = event.userId,
                            companyID = event.companyId,
                            onSuccess = {user->
                                if (user != null) {
                                    _author.value=user.copy()
                                    _uiErrState.value=_uiErrState.value.copy(isFetchAuthorErr = false, errMsg = "")
                                }
                            },
                            onFailure = {e->  _uiErrState.value=_uiErrState.value.copy(isFetchAuthorErr = false, errMsg = e)},
                            onUserNotFound = {_uiErrState.value= _uiErrState.value.copy(isFetchProcedureErr = true, errMsg = "User not found")}
                        )
                    }catch (e:Exception)
                    {
                        _uiErrState.value=_uiErrState.value.copy(isFetchAuthorErr = false, errMsg = e.message?:"ViewModel: Failed to fetch author")
                    }
                }


            }
            is VisualizeProcedureScreenEvent.OnNavigateToDisplayAllProcedureScreen->{
                viewModelScope.launch { _eventFlow.emit(VisualizeProcedureUiEvent.OnNavigateToDisplayAllProceduresScreen) }
            }
            is VisualizeProcedureScreenEvent.OnPhoto1Click->{
                viewModelScope.launch { _eventFlow.emit(VisualizeProcedureUiEvent.OnNavigateToDisplayPhotoScreen(
                    uri = _procedure.value.steps[event.index].photo1Uri
                )) }
            }
            is VisualizeProcedureScreenEvent.OnPhoto2Click->{
                viewModelScope.launch { _eventFlow.emit(VisualizeProcedureUiEvent.OnNavigateToDisplayPhotoScreen(
                    uri = _procedure.value.steps[event.index].photo2Uri
                )) }
            }

            is VisualizeProcedureScreenEvent.OnPhoto3Click->
                viewModelScope.launch { _eventFlow.emit(VisualizeProcedureUiEvent.OnNavigateToDisplayPhotoScreen(
                    uri=_procedure.value.steps[event.index].photo3Uri
                )) }

            is VisualizeProcedureScreenEvent.OnEditBtnClick->{
                //todo de implementat navigarea catre ecranul de edit. Mai ai de facut ecranul de edit, de retestat ecranul de cl-uri si facut la fel cu listItem si alte chestii de finete. Dupa nu stiu ce sa zic tb sa ne gandim cu statisticile cum facem
            }
        }
        }
    }
