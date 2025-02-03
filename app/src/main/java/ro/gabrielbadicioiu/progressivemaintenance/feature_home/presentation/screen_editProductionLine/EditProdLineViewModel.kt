package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.core.FirebaseCollections
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.EditProdLineUseCases

class EditProdLineViewModel(
    private val useCases: EditProdLineUseCases
):ViewModel() {
    private val db=Firebase.firestore
    private var isLoaded=false
//states
    private val _editedProdLine= mutableStateOf(ProductionLine())
    val editedProdLine:State<ProductionLine> = _editedProdLine

    private val _showDialog= mutableStateOf(false)
    val showDialog:State<Boolean> = _showDialog

    private val _isError= mutableStateOf(false)
    val isError:State<Boolean> = _isError
//one time events
    private val _eventFlow= MutableSharedFlow<EditProdLineUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()

    sealed class EditProdLineUiEvent()
    {
        data object ExitScreen:EditProdLineUiEvent()
        data class ToastMessage(val message:String):EditProdLineUiEvent()
    }

    fun onEvent(event: EditProdLineEvent)
    {
        when(event)
        {
            is EditProdLineEvent.OnLoadProductionLine->
                {   if (!isLoaded)
                {
                    viewModelScope.launch {
                        useCases.loadProdLine.execute(
                            id = event.id,
                            db = db,
                            onSuccess = {productionLine ->
                                if (productionLine != null) {
                                    _editedProdLine.value= productionLine.copy()
                                    isLoaded=true
                                }
                                else{
                                    _editedProdLine.value=_editedProdLine.value.copy(lineName = "failed")
                                }
                            },
                            onFailure = {e->
                                viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.ToastMessage(message = e)) }
                            }
                        )
                    }
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
            is EditProdLineEvent.OnCancelBtnClick->{
                onExitScreen()
            }
            is EditProdLineEvent.OnUpdateProdLine->{
                viewModelScope.launch {
                    useCases.onDoneEdit.execute(
                        db = db,
                        collection = FirebaseCollections.PRODUCTION_LINES,
                        updatedLine = _editedProdLine.value,
                        onSuccess = {onExitScreen()},
                        onFailure = {e->
                            viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.ToastMessage(e)) }
                        },
                        onEmptyName = {e->
                            _isError.value=true
                            viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.ToastMessage(e)) }
                        }
                    )
                }
            }
            is EditProdLineEvent.OnDeleteClick->{_showDialog.value=true}
            is EditProdLineEvent.OnAlertDialogDismiss->{_showDialog.value=false}
            is EditProdLineEvent.OnDeleteDialogConfirm->{
                viewModelScope.launch {
                    useCases.onDeleteEditProdLine.execute(
                        db = db,
                        collection =FirebaseCollections.PRODUCTION_LINES,
                        documentID = _editedProdLine.value.id,
                        onSuccess = {
                            _showDialog.value=false
                            onExitScreen()
                        },
                        onFailure = {e->
                            viewModelScope.launch {_eventFlow.emit(EditProdLineUiEvent.ToastMessage(e))}
                        }
                    )
                }
            }
            }//when
        }//onEvent
    private fun onExitScreen()
    {

        _isError.value=false
        isLoaded=false
        viewModelScope.launch { _eventFlow.emit(EditProdLineUiEvent.ExitScreen) }
    }
    }
