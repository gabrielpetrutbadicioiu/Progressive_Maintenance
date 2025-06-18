package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine

@Composable
fun EditProdLineCard(
    productionLine: ProductionLine,
    emptyNameError:Boolean,
    emptyNameErrorMsg:String,
    errorMsg:String,
    isError:Boolean,
    onAddEquipmentClick:()->Unit,
    onDeleteEquipment:(Int)->Unit,
    onLineNameChange:(String)->Unit,
   onEquipmentNameChange:(String, Int)->Unit,
    onDoneBtnClick:()->Unit,
    onCancelBtnClick:()->Unit,
    onDeleteProdLineClick:()->Unit
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.btn_color)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(text = stringResource(id = R.string.enter_line_name),
                            color = colorResource(id = R.color.text_color),
                            fontSize = 13.sp)
                    },
                    supportingText = {
                        Text(text = stringResource(id = R.string.required),
                            color = colorResource(id = R.color.text_color)
                        )
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.btn_color)
                    ),
                    trailingIcon = { IconButton(onClick = {
                        onDeleteEquipment(index)
                    },
                        ) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteOutline,
                            contentDescription = stringResource(id = R.string.icon_btn_descr),
                            tint = Color.Red
                        )
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
            }//items
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
                            tint = colorResource(id = R.color.btn_color)
                        )
                        Text(
                            text = stringResource(id = R.string.add_equipment_to_line),
                            color = colorResource(id = R.color.btn_color)
                        )
                    }
                }
            }//item
            //err msg
            item {
                if (emptyNameError)
                {
                    IconTextField(
                        text =emptyNameErrorMsg ,
                        icon = Icons.Default.WarningAmber,
                        color = Color.Red ,
                        iconSize = 24,
                        textSize =20 ,
                        clickEn = false
                    ) {

                    }
                }
                if (isError)
                {
                    IconTextField(
                        text =errorMsg ,
                        icon = Icons.Default.WarningAmber,
                        color = Color.Red ,
                        iconSize = 24,
                        textSize =20 ,
                        clickEn = false
                    ) {}
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically) {

                    OutlinedButton(onClick = {
                        onCancelBtnClick()
                    },
                        colors = ButtonDefaults.buttonColors(contentColor = colorResource(id = R.color.btn_color),
                            containerColor = Color.Transparent),
                        border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.btn_color))
                    ) {
                        Text(text = stringResource(id = R.string.cancel_btn))
                    }

                    Button(
                        onClick = {
                            onDoneBtnClick()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn_color))
                    ) {
                        Text(text = stringResource(id = R.string.done_btn))
                    }
                }
            }//item
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    OutlinedButton(
                        onClick = {
                        onDeleteProdLineClick()
                    },
                        colors = ButtonDefaults.buttonColors(contentColor = Color.Red,
                            containerColor = Color.Transparent),
                        border = BorderStroke(width = 2.dp, color = Color.Red)) {
                        Text(text = stringResource(id = R.string.delete_line),
                            color = Color.Red)
                    }
                }
            }//item

        }
    }
}