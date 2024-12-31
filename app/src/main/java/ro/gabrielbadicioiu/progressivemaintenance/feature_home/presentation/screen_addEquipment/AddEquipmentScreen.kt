package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addEquipment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.EnButton
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addEquipment.components.EquipmentTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEquipmentScreen(
viewModel: AddEquipmentViewModel
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { /*TODO*/
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                item{
                   EquipmentTextField(
                       label = stringResource(id = R.string.production_line_description),
                       value ="", //TODO
                       onValueChange = {},
                       isError =false )
                }
                items(viewModel.equipmentNr){
                    EquipmentTextField(
                        label = stringResource(id = R.string.equipment_name) ,
                        value = "",
                        onValueChange = {},//TODO
                        isError = false)
                }
                item {
                    TextButton(onClick = { viewModel.OnEvent(AddEquipmentEvent.OnAddEquipmentClick) }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.icon_descr),
                                tint = colorResource(id = R.color.bar_color),
                                modifier = Modifier.size(26.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.add_equipment),
                                fontSize = 24.sp,
                                color = colorResource(id = R.color.bar_color)
                            )
                        }
                    }
                }
                item{
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically){
                        EnButton(
                            onButtonClick = { /*TODO*/ },
                            btnEnabled = false,
                            text = stringResource(id = R.string.done_btn))
                        EnButton(
                            onButtonClick = { /*TODO*/ },
                            btnEnabled =true ,
                            text = stringResource(id = R.string.cancel_btn) )
                    }
                }
            }

        }//surface
    }
}
