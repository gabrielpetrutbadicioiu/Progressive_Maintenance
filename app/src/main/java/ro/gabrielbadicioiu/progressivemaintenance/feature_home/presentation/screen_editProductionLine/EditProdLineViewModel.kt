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

    private val _editedProdLine= mutableStateOf(ProductionLine())
    val editedProdLine:State<ProductionLine> = _editedProdLine

    private val _showDialog= mutableStateOf(false)
    val showDialog:State<Boolean> = _showDialog

    private val _fetchProdLineErr= mutableStateOf(false)
    val fetchProdLineErr:State<Boolean> = _fetchProdLineErr

    private val _errMsg= mutableStateOf("")
    val errMsg:State<String> = _errMsg
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
//                viewModelScope.launch {
//                    useCases.onDoneEdit.execute(
//                        db = db,
//                        collection = FirebaseCollections.PRODUCTION_LINES,
//                        updatedLine = _editedProdLine.value,
//                        onSuccess = {onExitScreen()},
//                        onFailure = {e->
//                            viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.ToastMessage(e)) }
//                        },
//                        onEmptyName = {e->
//                            _isError.value=true
//                            viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.ToastMessage(e)) }
//                        }
//                    )
//                }
            }
            is EditProdLineEvent.OnDeleteClick->{_showDialog.value=true}
            is EditProdLineEvent.OnAlertDialogDismiss->{_showDialog.value=false}
            is EditProdLineEvent.OnDeleteDialogConfirm->{
//                viewModelScope.launch {
//                    useCases.onDeleteEditProdLine.execute(
//                        db = db,
//                        collection =FirebaseCollections.PRODUCTION_LINES,
//                        documentID = _editedProdLine.value.id,
//                        onSuccess = {
//                            _showDialog.value=false
//                            onExitScreen()
//                        },
//                        onFailure = {e->
//                            viewModelScope.launch {_eventFlow.emit(EditProdLineUiEvent.ToastMessage(e))}
//                        }
//                    )
//                }
            }
            }//when
        }//onEvent
    private fun onNavigateBack()
    {

        _editedProdLine.value=ProductionLine()
        _errMsg.value=""
        _fetchProdLineErr.value=false
        viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.OnNavigateBack) }
    }
    }
