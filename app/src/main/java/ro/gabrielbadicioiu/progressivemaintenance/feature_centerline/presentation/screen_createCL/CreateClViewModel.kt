package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_createCL

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CreateClArgumentData
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CreateClErrorState
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.use_cases.CreateClUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateClViewModel(
    private val companiesRepository: CompaniesRepository,
    private val useCases: CreateClUseCases
):ViewModel()
{
 //states
    private val _args= mutableStateOf(CreateClArgumentData())

    private val _currentUser= mutableStateOf(UserDetails())
    val currentUser:State<UserDetails> = _currentUser

    private val _prodLine= mutableStateOf(ProductionLine())
    val prodLine:State<ProductionLine> = _prodLine

     var equipment:Equipment?= null
    var date:String=""

    private val _errState= mutableStateOf(CreateClErrorState())
    val errorState:State<CreateClErrorState> = _errState

    private val _clForm= mutableStateOf(CenterLineForm())
    val clForm:State<CenterLineForm> = _clForm
 // one time events
    private val _eventFlow= MutableSharedFlow<CreateClUiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()
    sealed class CreateClUiEvent{
        data object OnNavigateToHome:CreateClUiEvent()
        data class OnShowToast(val message:String):CreateClUiEvent()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: CreateCenterLineEvent)
    {
        when(event)
        {
            is CreateCenterLineEvent.OnNavigateHome->{
                viewModelScope.launch { _eventFlow.emit(CreateClUiEvent.OnNavigateToHome) }
            }
            is CreateCenterLineEvent.OnGetArgumentData->{
                _args.value=_args.value.copy(
                    companyId = event.companyId,
                    userId = event.userId,
                    lineId = event.lineId,
                    equipmentId = event.equipmentId)
                viewModelScope.launch {
                    try {
                        companiesRepository.getUserInCompany(
                            currentUserID = event.userId,
                            companyID = event.companyId,
                            onSuccess = {user->
                                if (user != null) {
                                    _currentUser.value=user
                                    _clForm.value=_clForm.value.copy(authorId = user.userID)
                                }
                                _errState.value= CreateClErrorState()
                            },
                            onFailure = {e-> _errState.value=_errState.value.copy(isFetchDataErr = true, errMsg = e)},
                            onUserNotFound = {_errState.value=_errState.value.copy(isFetchDataErr = true, errMsg = "User not found")}
                        )
                    }catch (e:Exception)
                    {
                        _errState.value=_errState.value.copy(isFetchDataErr = true, errMsg = e.message?:"ViewModel: Fetch User Err")
                    }

                }
                viewModelScope.launch {
                    try {
                       companiesRepository.getProductionLineById(
                           companyId = event.companyId,
                           productionLineId = event.lineId,
                           onSuccess = {line->
                               _prodLine.value=line
                               equipment=line.equipments.find { equipment -> equipment.id==event.equipmentId }
                               _clForm.value=_clForm.value.copy(lineId = line.id)
                           },
                           onFailure = {e-> _errState.value=_errState.value.copy(isFetchDataErr = true, errMsg = e)}
                       )
                    }
                    catch (e:Exception)
                    {
                        _errState.value=_errState.value.copy(isFetchDataErr = true, errMsg = e.message?:"ViewModel Failed to fetch prod line")
                    }
                    }
                if (event.isCreatingNewCl)
                {
                    val now = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    date=now.format(formatter)
                    _clForm.value=_clForm.value.copy(date = date)
                }

            }//get argument data

            is CreateCenterLineEvent.OnClNameChange->{
                _clForm.value=_clForm.value.copy(clName = event.clName)
            }
            is CreateCenterLineEvent.OnParameterNameChange->{
                _clForm.value=useCases.clParameterNameChange.execute(
                    clForm = _clForm.value,
                    index = event.index,
                    parameterName = event.parameterName
                )
            }
            is CreateCenterLineEvent.OnParameterValueChange->{
                _clForm.value=useCases.clParameterValueChange.execute(
                    clForm = _clForm.value,
                    index = event.index,
                    parameterValue = event.parameterValue
                )
            }
            is CreateCenterLineEvent.OnAddParameterClick->
            {
                _clForm.value=useCases.addClParameter.execute(clForm = _clForm.value)
            }
            is CreateCenterLineEvent.OnParameterDelete->{
                _clForm.value=useCases.deleteClParameter.execute(clForm = _clForm.value.copy(), index = event.index)
            }
            is CreateCenterLineEvent.OnSaveClick->{
                viewModelScope.launch {
                    try {
                        _errState.value=useCases.onSaveClick.execute(
                            clForm = _clForm.value,
                            companyId = _args.value.companyId,
                            equipmentId = _args.value.equipmentId,
                            productionLineId = _prodLine.value.id,
                            onFailure = {e->viewModelScope.launch { _eventFlow.emit(CreateClUiEvent.OnShowToast(e))}},
                            onSuccess = {
                                viewModelScope.launch {  _eventFlow.emit(CreateClUiEvent.OnNavigateToHome)  }

                            }

                            )


                    }catch (e:Exception)
                    {
                        _errState.value=_errState.value.copy(isFetchDataErr = true, errMsg = e.message?:"Viewmodel:Failed to save!")
                    }
                }

            }
        }
    }
}