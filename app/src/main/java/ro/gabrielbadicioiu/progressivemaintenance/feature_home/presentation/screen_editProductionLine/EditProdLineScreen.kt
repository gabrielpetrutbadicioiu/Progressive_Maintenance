package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine.components.DeleteAlertDialog
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine.components.EditProdLineCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProdLineScreen(
    viewModel: EditProdLineViewModel,
    navController: NavController,
    prodLineID:String
)
{
    val context= LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.onEvent(EditProdLineEvent.OnLoadProductionLine(id = prodLineID))
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is EditProdLineViewModel.EditProdLineUiEvent.ToastMessage->
                    {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                     }
                is EditProdLineViewModel.EditProdLineUiEvent.ExitScreen->
                    {
                    navController.navigate(Screens.HomeScreen)
                    }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_icon),
                                contentDescription = stringResource(id = R.string.image_descr),
                                modifier = Modifier.size(86.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.bar_color),
                        scrolledContainerColor = colorResource(id = R.color.scroll_bar_color)
                    )
                )
            },
        )
        { innerPadding ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                EditProdLineCard(
                    productionLine =viewModel.editedProdLine.value ,
                    emptyNameError = viewModel.isError.value,
                    onAddEquipmentClick = { viewModel.onEvent(EditProdLineEvent.OnAddEquipment)},
                    onDeleteEquipment ={index-> viewModel.onEvent(EditProdLineEvent.OnDeleteEditEquipment(index))} ,
                    onLineNameChange ={name->viewModel.onEvent(EditProdLineEvent.OnProdLineNameChange(name))} ,
                    onEquipmentNameChange ={name, index->
                        viewModel.onEvent(EditProdLineEvent.OnEquipmentNameChange(name, index))
                    },
                    onDoneBtnClick = { viewModel.onEvent(EditProdLineEvent.OnUpdateProdLine) },
                    onCancelBtnClick = {viewModel.onEvent(EditProdLineEvent.OnCancelBtnClick)},
                    onDeleteProdLineClick = {viewModel.onEvent(EditProdLineEvent.OnDeleteClick)})
            }
        }
        DeleteAlertDialog(
            showDialog = viewModel.showDialog.value,
            onDismiss = { viewModel.onEvent(EditProdLineEvent.OnAlertDialogDismiss) },
            onConfirm = {viewModel.onEvent(EditProdLineEvent.OnDeleteDialogConfirm)})

    }
}