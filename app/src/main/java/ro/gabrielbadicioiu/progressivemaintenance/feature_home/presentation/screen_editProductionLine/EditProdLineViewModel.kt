package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.EditProdLineUseCases

class EditProdLineViewModel(
    private val useCases: EditProdLineUseCases
):ViewModel() {
    private var companyId:String=""
    private var userId:String=""
    private var prodLineId:String=""
//states
    private val _isEmptyNameErr= mutableStateOf(false)
    val isEmptyNameErr:State<Boolean> = _isEmptyNameErr

    private val _emptyNameErrMsg= mutableStateOf("")
    val emptyNameErrMsg:State<String> = _emptyNameErrMsg

    private val _isUpdateErr= mutableStateOf(false)
    val isUpdateErr:State<Boolean> = _isUpdateErr

    private val _editedProdLine= mutableStateOf(ProductionLine())
    val editedProdLine:State<ProductionLine> = _editedProdLine

    private val _showDialog= mutableStateOf(false)
    val showDialog:State<Boolean> = _showDialog

    private val _fetchProdLineErr= mutableStateOf(false)
    val fetchProdLineErr:State<Boolean> = _fetchProdLineErr

    private val _errMsg= mutableStateOf("")
    val errMsg:State<String> = _errMsg

    private  val _isError= mutableStateOf(false)
    val isError:State<Boolean> = _isError
//one time events
    private val _eventFlow= MutableSharedFlow<EditProdLineUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()

    sealed class EditProdLineUiEvent()
    {
        data object OnNavigateBack:EditProdLineUiEvent()
        data class ToastMessage(val message:String):EditProdLineUiEvent()
    }

    fun onEvent(event: EditProdLineEvent)
    {
        when(event)
        {
            is EditProdLineEvent.OnNavigateBack->{
                onNavigateBack()
            }
            is EditProdLineEvent.OnFetchEditedLine->
                {
                    try {
                        companyId=event.companyId
                        viewModelScope.launch {
                            useCases.loadProdLine.execute(
                                companyId = event.companyId,
                                productionLineId = event.prodLineId,
                                onSuccess = {prodLine->
                                    _fetchProdLineErr.value=false
                                    _editedProdLine.value=prodLine
                                },
                                onFailure = {e->
                                    _fetchProdLineErr.value=true
                                    _errMsg.value=e
                                    viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.ToastMessage(e)) }
                                }
                            )
                        }
                    }catch (e:Exception)
                    {
                        _fetchProdLineErr.value=true
                        _errMsg.value="Failed to load prodLine: ${e.message}"
                        viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.ToastMessage(e.message.toString())) }
                    }

                }
            is EditProdLineEvent.OnProdLineNameChange->{
                _editedProdLine.value=_editedProdLine.value.copy(lineName = event.name.replaceFirstChar { char-> char.uppercase() })
            }
            is EditProdLineEvent.OnEquipmentNameChange->{
                _editedProdLine.value=useCases.onEquipmentEdit.execute(newName = event.name, index = event.index, productionLine = _editedProdLine.value)
            }
            is EditProdLineEvent.OnDeleteEditEquipment->{
                _editedProdLine.value=useCases.onDeleteEditEquipment.execute(productionLine = _editedProdLine.value, index = event.index)
            }
            is EditProdLineEvent.OnAddEquipment->{
                _editedProdLine.value=_editedProdLine.value.copy(equipments = _editedProdLine.value.equipments+Equipment())
            }

            is EditProdLineEvent.OnUpdateProdLine->{

                viewModelScope.launch {
                    try {
                        useCases.onDoneEdit.execute(
                            companyId = companyId,
                            updatedLine = _editedProdLine.value,
                            onSuccess = {
                                onNavigateBack()
                            },
                            onFailure = {e->
                                _isUpdateErr.value=true
                                _errMsg.value= e
                            },
                            onEmptyName = {e->
                                _isEmptyNameErr.value=true
                                _emptyNameErrMsg.value=e
                                viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.ToastMessage(e)) }
                            }

                        )
                    } catch (e:Exception)
                    {
                        _isError.value=true
                        _errMsg.value=e.message.toString()
                    }


                }
            }
            is EditProdLineEvent.OnDeleteClick->{_showDialog.value=true}
            is EditProdLineEvent.OnAlertDialogDismiss->{_showDialog.value=false}
            is EditProdLineEvent.OnDeleteDialogConfirm->
                {
                    val lineName=_editedProdLine.value.lineName
                    viewModelScope.launch {
                        try {
                            useCases.onDeleteEditProdLine.execute(
                                deletedProductionLine = _editedProdLine.value.copy(),
                                companyId = companyId,
                                onSuccess = {
                                    viewModelScope.launch {
                                        _eventFlow.emit(EditProdLineUiEvent.ToastMessage("Production line '$lineName' successfully deleted."))
                                        _showDialog.value=false
                                        onNavigateBack()
                                    }

                                },
                                onFailure = {e->
                                    _isError.value=true
                                    _errMsg.value=e
                                    _showDialog.value=false
                                }
                            )
                        }catch (e:Exception)
                        {
                            _isError.value=true
                            _errMsg.value=e.message?:"Error deleting production line"
                            _showDialog.value=false
                        }

                    }
            }
            }//when
        }//onEvent
    private fun onNavigateBack()
    {   _isError.value=false
        _isUpdateErr.value=false
        _isEmptyNameErr.value=false
        _emptyNameErrMsg.value=""
        _editedProdLine.value=ProductionLine()
        _errMsg.value=""
        _fetchProdLineErr.value=false
        viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.OnNavigateBack) }
    }
    }
