package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_editProcedure

import android.media.audiofx.DynamicsProcessing.Eq
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.ProcedureStep
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.use_cases.EditProcedureUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_createProcedure.CreateProcedureEvent
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_createProcedure.CreateProcedureViewModel.CreateProcedureUiEvent

class EditProcedureScreenViewModel(
    private val companiesRepository: CompaniesRepository,
    private val useCases: EditProcedureUseCases
): ViewModel() {
    //states
    private val _uiErrState= mutableStateOf(EditProcedureUiErrState())
    val uiErrState: State<EditProcedureUiErrState> = _uiErrState

    private val _procedure= mutableStateOf(Procedure())
    val procedure:State<Procedure> = _procedure

    private val _author= mutableStateOf(UserDetails())
    val author:State<UserDetails> =_author

    private val _productionLine= mutableStateOf(ProductionLine())
    val productionLine:State<ProductionLine> = _productionLine

    private val _equipment= mutableStateOf(Equipment())
    val equipment:State<Equipment> =_equipment

    //oneTimeEvents
    private val _eventFlow= MutableSharedFlow<EditProcedureUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class EditProcedureUiEvent{
        data object OnNavigateToVisualizeProcedure:EditProcedureUiEvent()

        data class ShowToast(val message:String):EditProcedureUiEvent()
    }
    //vars
    private var args=EditProcedureArgumentData()
    private var company:Company=Company()
    fun onEvent(event:EditProcedureScreenEvent)
    {
        when(event)
        {
            is EditProcedureScreenEvent.OnFetchArgumentData->{
                args=args.copy(
                    userId = event.userId,
                    companyId = event.companyId,
                    productionLineId = event.productionLineId,
                    equipmentId = event.equipmentId,
                    procedureId = event.procedureId
                )
                viewModelScope.launch {
                    try {
                        companiesRepository.fetchProcedureById(
                            companyId = event.companyId,
                            lineId = event.productionLineId,
                            procedureId = event.procedureId,
                            onSuccess = {prc ->
                                _procedure.value=prc
                                _uiErrState.value=_uiErrState.value.copy(isFetchProcedureErr = false, errMsg = "")

                                        },
                            onFailure = {e->
                                _uiErrState.value=_uiErrState.value.copy(isFetchProcedureErr = true, errMsg = e)
                            }
                        )
                    }catch (e:Exception)
                    {
                        _uiErrState.value=_uiErrState.value.copy(isFetchProcedureErr = true, errMsg = e.message?:"ViewModel:Failed to fetch procedure")
                    }
                }
                viewModelScope.launch {
                    try {
                        companiesRepository.getUserInCompany(
                            currentUserID = event.userId,
                            companyID = event.companyId,
                            onFailure = {e-> _uiErrState.value=_uiErrState.value.copy(isFetchAuthorErr = true, errMsg = e)},
                            onSuccess = {auth->
                                if (auth != null) {
                                    _author.value=auth
                                }
                                _uiErrState.value=_uiErrState.value.copy(isFetchAuthorErr = false)
                            },
                            onUserNotFound = {_uiErrState.value=_uiErrState.value.copy(isFetchAuthorErr = true, errMsg = "User not found!")}
                        )
                    }catch (e:Exception)
                    {
                        _uiErrState.value=_uiErrState.value.copy(isFetchAuthorErr = true, errMsg = e.message?:"ViewModel:Failed to fetch author")
                    }
                }

                viewModelScope.launch {
                    try {
                        companiesRepository.getProductionLineById(
                            companyId = event.companyId,
                            productionLineId = event.productionLineId,
                            onSuccess = {prodLine->
                                _productionLine.value=prodLine
                                _equipment.value= _productionLine.value.equipments.find { eq-> eq.id==event.equipmentId }?:Equipment()
                                _uiErrState.value=_uiErrState.value.copy(isFetchProdLineErr = true)},
                            onFailure = {e-> _uiErrState.value=_uiErrState.value.copy(isFetchProdLineErr = true, errMsg = e)}
                        )
                    }catch (e:Exception)
                    {
                        _uiErrState.value=_uiErrState.value.copy(isFetchProdLineErr = true, errMsg = e.message?:"ViewModel:Failed to fetch production line")
                    }
                }
                viewModelScope.launch {
                    try {
                        companiesRepository.getCompanyById(
                            companyId = event.companyId,
                            onSuccess = {comp-> company=comp
                                _uiErrState.value=_uiErrState.value.copy(isFetchCompanyErr = false)
                                        },
                            onFailure = {e-> _uiErrState.value=_uiErrState.value.copy(isFetchProdLineErr = true, errMsg = e)}
                        )
                    }catch (e:Exception)
                    {
                        _uiErrState.value=_uiErrState.value.copy(isFetchProdLineErr = true, errMsg = e.message?:"ViewModel:Failed to fetch company")
                    }
                }
            }

            is EditProcedureScreenEvent.OnNavigateToVisualizeProcedure->{
                viewModelScope.launch { _eventFlow.emit(EditProcedureUiEvent.OnNavigateToVisualizeProcedure) }
            }

            is EditProcedureScreenEvent.OnProcedureNameChange->{
                _procedure.value=_procedure.value.copy(procedureName = event.name)
            }
            is EditProcedureScreenEvent.OnUriResult->{
                viewModelScope.launch {
                    try {
                        useCases.onStepPhotoUriResult.execute(
                            procedure = _procedure.value.copy(),
                            companyName = company.organisationName,
                            index = event.index,
                            localUri = event.localUri,
                            onSuccess = {upProcedure->_procedure.value=upProcedure.copy()},
                            onFailure = {e-> viewModelScope.launch { _eventFlow.emit(
                                EditProcedureUiEvent.ShowToast(e)) } }
                        )
                    }catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(EditProcedureUiEvent.ShowToast(e.message?:"ViewModel:Failed to upload photo")) }
                    }
                }
            }
            is EditProcedureScreenEvent.OnPhoto1Delete->{
                viewModelScope.launch {
                    try {
                        useCases.procedurePhoto1Remove.execute(
                            procedure = _procedure.value.copy(),
                            companyName = company.organisationName,
                            index = event.index,
                            onSuccess = {updatedProcedure-> _procedure.value=updatedProcedure.copy()},
                            onFailure = {e->viewModelScope.launch { _eventFlow.emit(
                                EditProcedureUiEvent.ShowToast(e)) }}

                        )
                    } catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(EditProcedureUiEvent.ShowToast(e.message?:"ViewModel:Failed to delete photo1")) }
                    }
                }
            }

            is EditProcedureScreenEvent.OnPhoto2Delete->{
                viewModelScope.launch {
                    try {
                        useCases.procedurePhoto2Remove.execute(
                            procedure = _procedure.value.copy(),
                            companyName = company.organisationName,
                            index = event.index,
                            onSuccess = {updatedProcedure-> _procedure.value=updatedProcedure.copy()},
                            onFailure = {e->viewModelScope.launch { _eventFlow.emit(
                                EditProcedureUiEvent.ShowToast(e)) }}

                        )
                    } catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(EditProcedureUiEvent.ShowToast(e.message?:"ViewModel:Failed to delete photo2")) }
                    }
                }
            }
            is EditProcedureScreenEvent.OnPhoto3Delete->{
                viewModelScope.launch {
                    try {
                        useCases.procedurePhoto3Remove.execute(
                            procedure = _procedure.value.copy(),
                            companyName = company.organisationName,
                            index = event.index,
                            onSuccess = {updatedProcedure-> _procedure.value=updatedProcedure.copy()},
                            onFailure = {e->viewModelScope.launch { _eventFlow.emit(
                                EditProcedureUiEvent.ShowToast(e)) }}

                        )
                    } catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(EditProcedureUiEvent.ShowToast(e.message?:"ViewModel:Failed to delete photo3")) }
                    }
                }

            }
            is EditProcedureScreenEvent.OnStepDescrChange->{
                _procedure.value=useCases.onStepDescriptionChange.execute(procedure = _procedure.value, index = event.index, description = event.description)
            }
            is EditProcedureScreenEvent.OnAddNewStep->{
                val newStepList=_procedure.value.steps+ ProcedureStep()
                _procedure.value=_procedure.value.copy(steps = newStepList)
            }
            is EditProcedureScreenEvent.OnCancelBtnClick->{
                _procedure.value=Procedure()
                onEvent(EditProcedureScreenEvent.OnNavigateToVisualizeProcedure)
            }
            is EditProcedureScreenEvent.OnSaveBtnClick->{
                _uiErrState.value=useCases.onUpdateProcedure.execute(
                    procedure = _procedure.value,
                    onEmptyName = {e->
                        viewModelScope.launch { _eventFlow.emit(EditProcedureUiEvent.ShowToast(e)) }
                    },
                    onEmptyStep = {e->
                        viewModelScope.launch { _eventFlow.emit(EditProcedureUiEvent.ShowToast(e))}
                    },
                    onSuccess = {updatedProcedure->
                        viewModelScope.launch {
                            try {
                                companiesRepository.updateProcedure(
                                    companyId = args.companyId,
                                    lineId = args.productionLineId,
                                    procedure = updatedProcedure,
                                    onSuccess = {
                                        onEvent(EditProcedureScreenEvent.OnNavigateToVisualizeProcedure)
                                    },
                                    onFailure = {e-> viewModelScope.launch { _eventFlow.emit(EditProcedureUiEvent.ShowToast(e))}}
                                )
                            } catch (e:Exception)
                            {
                                viewModelScope.launch { _eventFlow.emit(EditProcedureUiEvent.ShowToast(e.message?:"ViewModel: Failed to update"))}
                            }
                        }
                    }
                )
            }
            is EditProcedureScreenEvent.OnDeleteProcedureStep->{
                val stepList=_procedure.value.steps.toMutableList()
                stepList-=stepList[event.index]
                _procedure.value=_procedure.value.copy(steps = stepList)
            }
        }
    }
}