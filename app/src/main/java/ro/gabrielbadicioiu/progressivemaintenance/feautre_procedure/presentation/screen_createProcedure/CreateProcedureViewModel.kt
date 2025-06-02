package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_createProcedure

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
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.CreateProcedureArgs
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.CreateProcedureScreenErrState
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.ProcedureStep
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.use_cases.CreateProcedureUseCases
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateProcedureViewModel(
    private val companiesRepository: CompaniesRepository,
    private val useCases: CreateProcedureUseCases
):ViewModel() {

    //states
    private val _procedure= mutableStateOf(Procedure())
    val procedure:State<Procedure> = _procedure

     private val _screenErrState= mutableStateOf(CreateProcedureScreenErrState())
     val screenErrState:State<CreateProcedureScreenErrState> = _screenErrState

    private val _currentUser= mutableStateOf(UserDetails())
    val currentUser:State<UserDetails> = _currentUser

    private val _prodLine= mutableStateOf(ProductionLine())
    val prodLine:State<ProductionLine> = _prodLine

    private val _equipment= mutableStateOf(Equipment())
    val equipment:State<Equipment> = _equipment

    //one time events
    private val _eventFlow= MutableSharedFlow<CreateProcedureUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class CreateProcedureUiEvent{
        data object OnNavigateHome:CreateProcedureUiEvent()

        data class OnShowToast(val message:String):CreateProcedureUiEvent()
    }
    //variables
    private var args=CreateProcedureArgs()
    private var company=Company()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: CreateProcedureEvent)
    {
        when(event)
        {
            is CreateProcedureEvent.OnFetchArgumentData->{
                args=CreateProcedureArgs(
                    companyId = event.companyId,
                    userId = event.userId,
                    productionLineId = event.productionLineId,
                    equipmentId = event.equipmentId)
                _procedure.value=_procedure.value.copy(
                    companyId = event.companyId,
                    authorId = event.userId,
                    lineId = event.productionLineId,
                    equipmentId = event.equipmentId
                )
                viewModelScope.launch {
                    companiesRepository.getUserInCompany(
                        companyID = event.companyId,
                        currentUserID = event.userId,
                        onSuccess = {user->
                            if (user != null) {
                                _currentUser.value=user.copy()
                            }
                        },
                        onFailure = {e->
                            _screenErrState.value=_screenErrState.value.copy(errMsg = e, isFetchDataErr = true)
                        },
                        onUserNotFound = {
                            _screenErrState.value=_screenErrState.value.copy(errMsg = "User not found", isFetchDataErr = true)
                        }
                    )
                }
                viewModelScope.launch {
                    companiesRepository.getProductionLineById(
                        companyId = event.companyId,
                        productionLineId = event.productionLineId,
                        onSuccess = {line->
                            _prodLine.value=line.copy()
                            line.equipments.forEach { eq-> if (eq.id==event.equipmentId) _equipment.value=eq.copy()}
                        },
                        onFailure = {e->
                            _screenErrState.value=_screenErrState.value.copy(errMsg = e, isFetchDataErr = true)
                        }
                    )
                }
                val now = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                val date=now.format(formatter)
                _procedure.value=_procedure.value.copy(date = date)
                viewModelScope.launch {
                    try {
                        companiesRepository.getCompanyById(
                            companyId = event.companyId,
                            onSuccess = {comp-> company=comp},
                            onFailure = {e->
                                _screenErrState.value=_screenErrState.value.copy(errMsg = e, isFetchDataErr = true)
                            }
                        )
                    } catch (e:Exception)
                    {
                        _screenErrState.value=_screenErrState.value.copy(isFetchDataErr = true, errMsg = e.message?:"ViewModel:Failed to fetch company")
                    }

                }

            }
            is CreateProcedureEvent.OnNavigateHome->{
                _procedure.value=Procedure()
                viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnNavigateHome) }
            }
            is CreateProcedureEvent.OnProcedureNameChange->{
                _procedure.value=_procedure.value.copy(procedureName = event.procedureName)
            }
            is CreateProcedureEvent.OnSaveBtnClick->{

                   _screenErrState.value= useCases.onSaveProcedureClick.execute(
                        procedure = _procedure.value.copy(),
                        onEmptyName = {e->
                            viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e)) }
                        },
                       onEmptyStep = {e->viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e)) }},
                       onSuccess = {updatedProcedure->
                           viewModelScope.launch {
                               try {
                                   companiesRepository.uploadProcedure(
                                       companyId = args.companyId,
                                       lineId = args.productionLineId,
                                       procedure = updatedProcedure.copy(),
                                       onSuccess = {onEvent(CreateProcedureEvent.OnNavigateHome)},
                                       onFailure = {e->viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e)) }}
                                   )
                               }catch (e:Exception)
                               {
                                   viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e.message?:"ViewModel:Failed to upload procedure")) }
                               }
                           }
                       }
                    )

            }
            is CreateProcedureEvent.OnStepDescrChange->{
                _procedure.value=useCases.onStepDescriptionChange.execute(procedure = _procedure.value, index = event.index, description = event.description)
            }
            is CreateProcedureEvent.OnStepPhotoUriResult->{
                viewModelScope.launch {
                    try {
                        useCases.onStepPhotoUriResult.execute(
                            procedure = _procedure.value.copy(),
                            companyName = company.organisationName,
                            index = event.index,
                            localUri = event.localUri,
                            onSuccess = {upProcedure->_procedure.value=upProcedure.copy()},
                            onFailure = {e-> viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e)) } }
                        )
                    }catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e.message?:"ViewModel:Failed to upload photo")) }
                    }
                }
            }
            is CreateProcedureEvent.OnPhoto1Delete->{
                viewModelScope.launch {
                    try {
                        useCases.procedurePhoto1Remove.execute(
                            procedure = _procedure.value.copy(),
                            companyName = company.organisationName,
                            index = event.index,
                            onSuccess = {updatedProcedure-> _procedure.value=updatedProcedure.copy()},
                            onFailure = {e->viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e)) }}

                        )
                    } catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e.message?:"ViewModel:Failed to delete photo1")) }
                    }
                }
            }

            is CreateProcedureEvent.OnPhoto2Delete->{
                viewModelScope.launch {
                    try {
                        useCases.procedurePhoto2Remove.execute(
                            procedure = _procedure.value.copy(),
                            companyName = company.organisationName,
                            index = event.index,
                            onSuccess = {updatedProcedure-> _procedure.value=updatedProcedure.copy()},
                            onFailure = {e->viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e)) }}

                        )
                    } catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e.message?:"ViewModel:Failed to delete photo2")) }
                    }
                }
            }
            is CreateProcedureEvent.OnPhoto3Delete->{
                viewModelScope.launch {
                    try {
                        useCases.procedurePhoto3Remove.execute(
                            procedure = _procedure.value.copy(),
                            companyName = company.organisationName,
                            index = event.index,
                            onSuccess = {updatedProcedure-> _procedure.value=updatedProcedure.copy()},
                            onFailure = {e->viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e)) }}

                        )
                    } catch (e:Exception)
                    {
                        viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnShowToast(e.message?:"ViewModel:Failed to delete photo3")) }
                    }
                }

            }
            is CreateProcedureEvent.OnCancelBtnClick->{
                _procedure.value=Procedure()
                viewModelScope.launch { _eventFlow.emit(CreateProcedureUiEvent.OnNavigateHome) }
            }
            is CreateProcedureEvent.OnAddNewStep->{
                val newStepList=_procedure.value.steps+ProcedureStep()
                _procedure.value=_procedure.value.copy(steps = newStepList)
            }
        }
    }
}