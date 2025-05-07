package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine


@Composable
fun AddLineCard(
    productionLine: ProductionLine,
    emptyNameError:Boolean,
    onAddEquipmentClick:()->Unit,
    onDeleteEquipment:(Int)->Unit,
   onLineNameChange:(String)->Unit,
   onEquipmentNameChange:(String, Int)->Unit,
    onDoneBtnClick:()->Unit,
    onCancelBtnClick:()->Unit,
    showProgressBar:Boolean
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.bar_color))
    ) {
        LazyColumn(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            item {
                OutlinedTextField(
                    value = productionLine.lineName,
                    onValueChange = {
                        newName-> onLineNameChange(newName)
                                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = emptyNameError,
                    textStyle = TextStyle(color = colorResource(id = R.color.text_color)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.text_color)),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(text = stringResource(id = R.string.enter_line_name),
                            color = colorResource(id = R.color.text_color),
                            fontSize = 13.sp)
                    },
                    supportingText = {
                        Text(text = stringResource(id = R.string.required),
                            color = colorResource(id = R.color.text_color))
                    }
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(6.dp))
            }
            items(productionLine.equipments.size) { index ->
                OutlinedTextField(
                    value = productionLine.equipments[index].name,
                    onValueChange = {name->
                        onEquipmentNameChange(name, index)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = TextStyle(color = colorResource(id = R.color.text_color)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = colorResource(id = R.color.text_color)),
//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        containerColor = Color.Transparent,
//                        focusedBorderColor = colorResource(id = R.color.text_color),
//                    ),
                    trailingIcon = { IconButton(onClick = {
                        onDeleteEquipment(index)
                    }) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = stringResource(id = R.string.icon_btn_descr),
                            tint = colorResource(id = R.color.text_color))
                    } },
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_equipment_name),
                            color = colorResource(id = R.color.text_color),
                            fontSize = 10.sp
                        )
                    }
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(6.dp)
                )
            }
            item {
                TextButton(onClick = {
                    onAddEquipmentClick()
                }) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = stringResource(id = R.string.icon_btn_descr),
                            tint = colorResource(id = R.color.btn_color))
                        Text(
                            text = stringResource(id = R.string.add_equipment_to_line),
                            color = colorResource(id = R.color.btn_color))
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically) {
                    
                    
                        Button(
                            onClick = {
                                onDoneBtnClick()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn_color))
                        ) {
                            if (showProgressBar)
                            {
                                CircularProgressIndicator()
                            }
                            else {
                                Text(text = stringResource(id = R.string.done_btn))
                            }
                           
                        }
                    

                    Button(onClick = {
                        onCancelBtnClick()
                    },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn_color))) {
                        Text(text = stringResource(id = R.string.cancel_btn))
                    }
                }
            }

        }
    }
}

