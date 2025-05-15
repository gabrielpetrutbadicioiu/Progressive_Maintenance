package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine


@Composable
fun ProductionLineCard(
    userRank: String,
    productionLine: ProductionLine,
    onExpandClick:()->Unit,
    onEditClick:()->Unit,
    isExpanded:Boolean,
    isProductionLineDropDownExpanded:Boolean,
    onEquipmentClick:(equipment:Equipment)->Unit,
    onDropDownDismiss:()->Unit,
    onLogInterventionClick:()->Unit,
    onProductionLineClick:()->Unit,
    onViewProductionLineInterventionsClick:()->Unit,
    onDismissLineDropDown:()->Unit

)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.bar_color)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = productionLine.lineName,
                        style = MaterialTheme.typography.titleMedium,
                        color = colorResource(id = R.color.text_color))
                    IconButton(onClick = { onProductionLineClick()}) {
                        Icon(
                            imageVector = Icons.Outlined.ChevronRight,
                            contentDescription = stringResource(id = R.string.icon_descr),
                            tint = colorResource(id = R.color.text_color)
                            )
                        DropdownMenu(
                            expanded = isProductionLineDropDownExpanded,
                            onDismissRequest = { onDismissLineDropDown() }) {
                            DropdownMenuItem(text = {
                                Text(text = stringResource(id = R.string.view_interventions))
                            }, onClick = { onViewProductionLineInterventionsClick() })
                        }
                    }
                    Row(modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {

                        //edit
                        if (userRank!=UserRank.USER.name)
                        {
                            IconButton(onClick = {onEditClick() }) {
                                Icon(imageVector =Icons.Default.Edit,
                                    contentDescription = stringResource(id = R.string.icon_btn_descr),
                                    tint = colorResource(id = R.color.text_color))
                            }
                        }

                        IconButton(onClick = { onExpandClick() }) {
                            val icon= if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown
                            Icon(imageVector =icon,
                                contentDescription = stringResource(id = R.string.icon_btn_descr),
                                tint = colorResource(id = R.color.text_color))
                        }

                    }


                }
            if(isExpanded){
                Column(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    productionLine.equipments.forEach {
                        machine->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onEquipmentClick(machine)
                            },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = machine.name,
                                color =colorResource(id = R.color.text_color),
                                modifier = Modifier
                                    .padding(4.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Icon(
                                imageVector = Icons.Outlined.ChevronRight,
                                contentDescription = stringResource(id = R.string.icon_descr))
                            MachineDropDownMenu(
                                isDropDownMenuExpanded =machine.isExpanded ,
                                onDismissRequest = { onDropDownDismiss() },
                                onLogInterventionClick = {onLogInterventionClick()})

                        }
                       
                        HorizontalDivider( color = Color.LightGray)
                    }
                }
            }
        }

    }
}