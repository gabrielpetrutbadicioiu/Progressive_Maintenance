package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.composables

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.Divider
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.InterventionParticipants
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.PmCardErrorState
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterventionInfoSection(
    author: UserDetails,
    selectedShift: String,
    isOtherParticipantsMenuExpanded:Boolean,
    isShiftDropDownExpanded:Boolean,
    employeeList: List<UserDetails>,
    participantsList: List<InterventionParticipants>,
    fetchEmployeesErr:Boolean,
    fetchEmployeesErrMsg:String,
    showStartDateDialog:Boolean,
    downtimeStartDate:String,
    showEndDateDialog:Boolean,
    downtimeEndDate:String,
    downTimeStartTime:String,
    downTimeEndTime:String,
    showDownTimeStartDialog:Boolean,
    showDownTimeEndDialog:Boolean,
    pmCardErrorState: PmCardErrorState,
    lineName:String,
    equipmentName:String,
    isNewIntervention:Boolean,
    isEditing:Boolean,

    onSelectShiftClick: () -> Unit,
    onShiftDropDownDismiss:()->Unit,
    onShiftClick:(String)->Unit,
    onOtherParticipantsMenuDismiss:()->Unit,
    onOtherParticipantsMenuDisplay:()->Unit,
    onParticipantClick:(participant:UserDetails)->Unit,
    onRemoveParticipant:(participant:InterventionParticipants)->Unit,
    onStartDateDismiss:()->Unit,
    onSelectStartDateClick:()->Unit,
    onGetStartDate:(String)->Unit,
    onEndDateDismiss:()->Unit,
    onSelectEndDateClick:()->Unit,
    onGetEndDate:(String)->Unit,
    onShowDownTimeStartDialog:()->Unit,
    onShowDownTimeEndDialog:()->Unit,
    onGetDowntimeStartTime:(String)->Unit,
    onGetDowntimeEndTime:(String)->Unit,
    onStartTimeDismiss:()->Unit,
    onEndTimeDismiss:()->Unit,
    onGetTotalDowntimeDuration:(String)->Unit

    ) {

    val startDatePickerState= rememberDatePickerState()
    val endDatePickerState= rememberDatePickerState()
    val context= LocalContext.current
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = lineName)
            Text(text = equipmentName)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.author),
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DisplayUserWithAvatar(participant = author,
                modifier = Modifier.weight(0.6f))
            if (!isEditing && !isNewIntervention)
            {
                Text(text = selectedShift,
                    modifier = Modifier.padding(end = 16.dp))
            }
            else{
                OutlinedTextField(
                    value = selectedShift,
                    onValueChange = {},
                    isError = pmCardErrorState.isShiftErr,
                    readOnly = true,
                    placeholder = { Text(stringResource(id = R.string.shift)) },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = colorResource(id = R.color.btn_color)),
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

        }


        if (participantsList.isNotEmpty())
        {
            Divider(space = 8.dp, thickness = 2.dp, color = Color.DarkGray)
            Text(text = stringResource(id = R.string.other_participants),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 16.dp))
        }


        participantsList.forEach { participant->

            Divider(space = 8.dp, thickness = 1.dp, color = Color.DarkGray)
            Row {
                DisplayParticipantWithAvatar(participant = participant, modifier = Modifier.weight(1f))
                if (isEditing || isNewIntervention)
                {
                    IconButton(onClick = { onRemoveParticipant(participant) }) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = stringResource(id = R.string.icon_descr) )
                    }
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color.DarkGray,
                thickness = 1.dp)
        }
        //add participants btn
        if (isEditing || isNewIntervention)
        {
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

        }
        Divider(space = 16.dp, thickness = 2.dp, color = Color.DarkGray)

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.downtime_duration),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            //Date
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditing||isNewIntervention)
                {
                    OutlinedTextField(
                        value = downtimeStartDate,
                        onValueChange = {},
                        isError = pmCardErrorState.isStartDateErr,
                        readOnly = true,
                        placeholder = { Text(text = stringResource(id = R.string.date_format))},
                        modifier = Modifier.weight(1f),
                        supportingText = { Text(text = stringResource(id = R.string.select_start_date))},
                        colors = TextFieldDefaults.colors(focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                        trailingIcon = {
                            IconButton(onClick = {
                                onSelectStartDateClick()
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.DateRange,
                                    contentDescription = stringResource(id = R.string.icon_descr)
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = downtimeEndDate,
                        onValueChange = {},
                        isError = pmCardErrorState.isEndDateErr,
                        placeholder = { Text(text = stringResource(id = R.string.date_format))},
                        readOnly = true,
                        modifier = Modifier.weight(1f),
                        supportingText = { Text(text = stringResource(id = R.string.select_end_date))},
                        colors = TextFieldDefaults.colors(focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                        trailingIcon = {
                            IconButton(onClick = {onSelectEndDateClick()}) {
                                Icon(
                                    imageVector = Icons.Outlined.DateRange,
                                    contentDescription = stringResource(id = R.string.icon_descr)
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                else{
                    OutlinedTextField(
                        value = downtimeStartDate,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.weight(1f),
                        supportingText = { Text(text = stringResource(id = R.string.downtime_start_date))},
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(id = R.color.btn_color)
                            ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = stringResource(id = R.string.icon_descr)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = downtimeEndDate,
                        onValueChange = {},
                        readOnly = true,
                        supportingText = { Text(text = stringResource(id = R.string.downtime_end_date))},
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = stringResource(id = R.string.icon_descr)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                }



            //hour
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isEditing || isNewIntervention)
                {
                    OutlinedTextField(
                        value = downTimeStartTime,
                        onValueChange = {},
                        isError = pmCardErrorState.isStartTimeErr,
                        readOnly = true,
                        placeholder = { Text(text = stringResource(id = R.string.time_format))},
                        modifier = Modifier.weight(1f),
                        supportingText = { Text(text = stringResource(id = R.string.downtime_start))},
                        colors = TextFieldDefaults.colors(focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                        trailingIcon = {
                            IconButton(onClick = {
                                onShowDownTimeStartDialog()
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.AccessTime,
                                    contentDescription = stringResource(id = R.string.icon_descr)
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = downTimeEndTime,
                        onValueChange = {},
                        isError = pmCardErrorState.isEndTimeErr,
                        placeholder = { Text(text = stringResource(id = R.string.time_format))},
                        readOnly = true,
                        modifier = Modifier.weight(1f),
                        supportingText = { Text(text = stringResource(id = R.string.downtime_end))},
                        colors = TextFieldDefaults.colors(focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                        trailingIcon = {
                            IconButton(onClick = {onShowDownTimeEndDialog()}) {
                                Icon(
                                    imageVector = Icons.Outlined.AccessTime,
                                    contentDescription = stringResource(id = R.string.icon_descr)
                                )
                            }
                        }
                    )
                }
                else{
                    OutlinedTextField(
                        value = downTimeStartTime,
                        onValueChange = {},
                        readOnly = true,
                        supportingText = { Text(text = stringResource(id = R.string.downtime_start_time))},
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(id = R.color.btn_color)
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = stringResource(id = R.string.icon_descr)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = downTimeEndTime,
                        onValueChange = {},
                        readOnly = true,
                        supportingText = { Text(text = stringResource(id = R.string.downtime_end_time))},
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = colorResource(id = R.color.btn_color)
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = stringResource(id = R.string.icon_descr)
                            )
                        }
                    )
                }

            }
            if (
                downtimeStartDate.isNotEmpty() &&
                downtimeEndDate.isNotEmpty() &&
                downTimeStartTime.isNotEmpty() &&
                downTimeEndTime.isNotEmpty()
            ) {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                val start = LocalDateTime.parse("$downtimeStartDate $downTimeStartTime", formatter)
                val end = LocalDateTime.parse("$downtimeEndDate $downTimeEndTime", formatter)

                if (!end.isBefore(start))
                {
                    val duration = Duration.between(start, end)
                    val totalMinutes = duration.toMinutes()

                    val display = "${totalMinutes / 60}h ${totalMinutes % 60}m"

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = "${stringResource(id = R.string.total_downtime_duration)} $display ",
                            onValueChange = {},
                            readOnly = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent))
                    onGetTotalDowntimeDuration(display)
                }

            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.DarkGray,
                thickness = 2.dp)
            Spacer(modifier = Modifier.height(16.dp))
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
                LazyColumn(horizontalAlignment = Alignment.Start) {
                    items(employeeList.size)
                    {index->
                        HorizontalDivider(color = Color.DarkGray,
                            thickness = 1.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { onParticipantClick(employeeList[index].copy()) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            contentPadding = PaddingValues(0.dp),
                            elevation = ButtonDefaults.buttonElevation(0.dp)
                        ) {
                            ClickableUser(participant = employeeList[index])
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

    }

    if (showDownTimeEndDialog)
    {
        val endTimeDialog=TimePickerDialog (context, {
                _,hour, minute->
            val h= if (hour<10) "0$hour" else hour
            val min= if (minute<10)"0$minute" else minute
            onGetDowntimeEndTime("$h:$min")
        },
            LocalTime.now().hour,
            LocalTime.now().minute,
            false)
            endTimeDialog.show()
            endTimeDialog.setOnCancelListener {
                onEndTimeDismiss()
            }
    }

    if (showDownTimeStartDialog)
    {
       val startTimeDialog= TimePickerDialog (context, {
            _,hour, minute->
           val h= if (hour<10) "0$hour" else hour
           val min= if (minute<10)"0$minute" else minute
            onGetDowntimeStartTime("$h:$min")
        },
            LocalTime.now().hour,
            LocalTime.now().minute,
            false)
        startTimeDialog.show()
        startTimeDialog.setOnCancelListener {
            onStartTimeDismiss()
        }
    }

    if (showEndDateDialog)
    {
        DatePickerDialog(
            onDismissRequest = { onEndDateDismiss() },
            confirmButton = {
                TextButton(onClick = { onEndDateDismiss() }) {
                    Text(text = stringResource(id = R.string.done_btn))}
            },
            dismissButton = {
                TextButton(onClick = { onEndDateDismiss() }) {
                    Text(text = stringResource(id = R.string.cancel_btn))}
            }) {
            DatePicker(state = endDatePickerState)
        }
    }
    if (showStartDateDialog)
    {
        DatePickerDialog(
            onDismissRequest = { onStartDateDismiss() },
            confirmButton = {
                TextButton(onClick = { onStartDateDismiss() }) {
                Text(text = stringResource(id = R.string.done_btn))} },
            dismissButton = {
                TextButton(onClick = {onStartDateDismiss()}) {
                    Text(text = stringResource(id = R.string.cancel_btn))}
            }
            ) {
            DatePicker(state = startDatePickerState)
        }

}
    startDatePickerState.selectedDateMillis?.let { millis->
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val startDate=Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
        onGetStartDate(startDate.toString())
    }
    endDatePickerState.selectedDateMillis?.let {millis->
        val formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val endDate=Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
        onGetEndDate(endDate)
    }
}





