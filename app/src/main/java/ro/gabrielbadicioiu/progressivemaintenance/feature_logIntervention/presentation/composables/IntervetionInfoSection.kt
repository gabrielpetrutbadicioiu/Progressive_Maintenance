package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterventionInfoSection(
    author: UserDetails,
    selectedShift: String,
    isOtherParticipantsMenuExpanded:Boolean,
    isShiftDropDownExpanded:Boolean,
    employeeList: List<UserDetails>,
    participantsList: List<UserDetails>,
    fetchEmployeesErr:Boolean,
    fetchEmployeesErrMsg:String,

    onSelectShiftClick: () -> Unit,
    onShiftDropDownDismiss:()->Unit,
    onShiftClick:(String)->Unit,
    onOtherParticipantsMenuDismiss:()->Unit,
    onOtherParticipantsMenuDisplay:()->Unit,
    onParticipantClick:(participant:UserDetails)->Unit,
    onRemoveParticipant:(participant:UserDetails)->Unit,


    ) {


    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.author),
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DisplayUserWithAvatar(user = author,
                modifier = Modifier.weight(0.6f))

            OutlinedTextField(
                value = selectedShift,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text(stringResource(id = R.string.shift)) },
                colors = TextFieldDefaults.colors(focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                trailingIcon = {
                    IconButton(onClick = onSelectShiftClick) {
                        Icon(
                            imageVector = if (isShiftDropDownExpanded) Icons.Outlined.ArrowDropUp else Icons.Outlined.ArrowDropDown,
                            contentDescription = stringResource(id = R.string.icon_descr)
                        )
                        DropdownMenu(
                            expanded = isShiftDropDownExpanded,
                            onDismissRequest = onShiftDropDownDismiss
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.shiftA)) },
                                onClick = { onShiftClick("Shift A") }
                            )
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.shiftB)) },
                                onClick = { onShiftClick("Shift B") }
                            )
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.shiftC)) },
                                onClick = { onShiftClick("Shift C") }
                            )
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.shiftD)) },
                                onClick = { onShiftClick("Shift D") }
                            )
                            DropdownMenuItem(
                                text = { Text(text = stringResource(id = R.string.no_shift)) },
                                onClick = { onShiftClick("N/A") }
                            )
                        }
                    }
                },
                modifier = Modifier.weight(0.4f)
            )
        }


        if (participantsList.isNotEmpty())
        {
            HorizontalDivider(color = Color.DarkGray,
                thickness = 2.dp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.other_participants),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 16.dp))
        }


        participantsList.forEach { participant->
            HorizontalDivider(color = Color.DarkGray,
                thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                DisplayUserWithAvatar(user = participant, modifier = Modifier.weight(1f))
                IconButton(onClick = { onRemoveParticipant(participant) }) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = stringResource(id = R.string.icon_descr) )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color.DarkGray,
                thickness = 1.dp)
        }
        //add participants btn
        TextButton(onClick = { onOtherParticipantsMenuDisplay() }) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.add_other_participants),
                    color = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Outlined.PersonAdd,
                    contentDescription = stringResource(id = R.string.icon_descr),
                    tint = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color.DarkGray,
            thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.downtime_duration),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text(text = stringResource(id = R.string.start_date))},
                    modifier = Modifier.weight(1f),
                    supportingText = { Text(text = stringResource(id = R.string.select_start_date))},
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                    trailingIcon = {
                        IconButton(onClick = {
                            /*todo*/
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = stringResource(id = R.string.icon_descr)
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text(text = stringResource(id = R.string.end_date))},
                    readOnly = true,
                    modifier = Modifier.weight(1f),
                    supportingText = { Text(text = stringResource(id = R.string.select_end_date))},
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                    trailingIcon = {
                        IconButton(onClick = {/*todo*/ }) {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = stringResource(id = R.string.icon_descr)
                            )
                        }
                    }
                )
            }
        }


    }




    if (isOtherParticipantsMenuExpanded)
    {
        if (fetchEmployeesErr)
        {
            IconTextField(
                text = fetchEmployeesErrMsg,
                icon = Icons.Outlined.WarningAmber,
                color = Color.Red,
                iconSize = 32,
                textSize =48 ,
                clickEn = false) {}
        }
        else{
            ModalBottomSheet(onDismissRequest = { onOtherParticipantsMenuDismiss() }) {
                LazyColumn {
                    items(employeeList.size)
                    {index->
                        HorizontalDivider(color = Color.DarkGray,
                            thickness = 1.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                        DisplayUserWithAvatar(
                            user =employeeList[index],
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onParticipantClick(employeeList[index].copy())
                                })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

    }



}





