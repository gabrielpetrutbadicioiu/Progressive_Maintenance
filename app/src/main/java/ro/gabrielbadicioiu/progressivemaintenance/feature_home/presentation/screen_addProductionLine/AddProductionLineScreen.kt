package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine.components.AddLineCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductionLineScreen(
    viewModel: AddProductionLineViewModel,
    companyID:String,
    userID:String,
    navController: NavController
){
val context= LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(AddProductionLineEvent.OnGetArgumentData(companyID = companyID, currentUserId = userID))
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is AddProductionLineViewModel.AddEquipmentUiEvent.OnExitScreen->{
                    navController.navigate(Screens.HomeScreen(companyID = companyID, userID = userID))
                }
                is AddProductionLineViewModel.AddEquipmentUiEvent.ToastMessage->{
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is AddProductionLineViewModel.AddEquipmentUiEvent.OnNavigateUp->{
                    navController.navigate(Screens.HomeScreen(companyID = companyID, userID=userID))
                }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
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

                        }
                    },
                    navigationIcon = {
                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {viewModel.onEvent(AddProductionLineEvent.OnNavigateUp)}) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = stringResource(id = R.string.icon_descr),
                                    tint = colorResource(id = R.color.text_color))
                            }
                            Text(text = stringResource(id = R.string.home_btn),
                                color = colorResource(id = R.color.text_color))
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
                    onCancelBtnClick = {viewModel.onEvent(AddProductionLineEvent.OnNavigateUp)},
                    onDoneBtnClick = {viewModel.onEvent(AddProductionLineEvent.OnDoneBtnClick)},
                    showProgressBar = viewModel.showProgressBar.value
                )
            }
       }
    }
}

