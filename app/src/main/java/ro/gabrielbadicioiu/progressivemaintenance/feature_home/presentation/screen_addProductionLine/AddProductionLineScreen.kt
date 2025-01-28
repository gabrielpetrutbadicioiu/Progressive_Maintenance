package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine

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
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine.components.AddLineCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductionLineScreen(
    viewModel: AddProductionLineViewModel,
    navController: NavController
){
val context= LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is AddProductionLineViewModel.AddEquipmentUiEvent.OnExitScreen->{
                    navController.navigate(Screens.HomeScreen)
                }
                is AddProductionLineViewModel.AddEquipmentUiEvent.ToastMessage->{
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
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
                AddLineCard(
                    productionLine =viewModel.productionLine.value ,
                    emptyNameError = viewModel.isError.value,
           onAddEquipmentClick = { viewModel.onEvent(AddProductionLineEvent.OnAddEquipmentClick) },
          onDeleteEquipment ={index-> viewModel.onEvent(AddProductionLineEvent.OnEquipmentDelete(index = index))} ,
              onLineNameChange = {newName->viewModel.onEvent(AddProductionLineEvent.OnProductionLineNameChange(newName))},
           onEquipmentNameChange ={newName, index->
               viewModel.onEvent(AddProductionLineEvent.OnEquipmentNameChange(name = newName, index = index))},
                    onCancelBtnClick = {viewModel.onEvent(AddProductionLineEvent.OnExitScreen)},
                    onDoneBtnClick = {viewModel.onEvent(AddProductionLineEvent.OnDoneBtnClick)}
                )
            }
       }
    }
}

