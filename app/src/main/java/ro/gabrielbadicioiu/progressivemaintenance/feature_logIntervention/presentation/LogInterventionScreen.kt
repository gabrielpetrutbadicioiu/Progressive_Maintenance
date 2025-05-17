package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.composables.InterventionInfoSection
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.composables.InterventionSummarySection

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInterventionScreen(
    companyId:String,
    userId:String,
    productionLineId:String,
    equipmentId:String,
    equipmentName:String,
    prodLineName:String,
    readOnly:Boolean,
    interventionId:String,

    viewModel: LogInterventionScreenViewModel,
    navController: NavController
)
{
    val context= LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(LogInterventionScreenEvent.GetArgumentData(
            companyId = companyId,
            userId=userId,
            equipmentName = equipmentName,
            equipmentId = equipmentId,
            prodLineName = prodLineName,
            productionLineId = productionLineId,
            interventionId = interventionId,
            readOnly = readOnly
            ))
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is LogInterventionScreenViewModel.LogInterventionUiEvent.ShowToast->{
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is LogInterventionScreenViewModel.LogInterventionUiEvent.OnNavigateToHome->{
                    navController.navigate(Screens.HomeScreen(companyID = companyId, userID = userId))
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
                    navigationIcon = { IconButton(onClick = {viewModel.onEvent(LogInterventionScreenEvent.OnNavigateToHome) }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = stringResource(id = R.string.icon_descr) )
                    }},
                    title = {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.log_intervention))
                            Spacer(modifier = Modifier.width(4.dp))
                            DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.fix_animation), size = 64.dp)

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
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top)
            { if (viewModel.fetchAuthorError.value)
            {
                item {
                    IconTextField(
                        text = viewModel.fetchAuthorErrMsg.value,
                        icon = Icons.Outlined.WarningAmber,
                        color = Color.Red,
                        iconSize =48,
                        textSize =24,
                        clickEn =false) {}
                }

          }
                else{
                    item {  InterventionInfoSection(
                        author = viewModel.author.value.copy(),
                        isOtherParticipantsMenuExpanded = viewModel.isOtherParticipantsDropdownMenuExpanded.value,
                        selectedShift = viewModel.pmCard.value.shift,
                        isShiftDropDownExpanded = viewModel.isShiftDropDownMenuExpanded.value,
                        participantsList = viewModel.participantsList.value,
                        employeeList = viewModel.employeeList.value,
                        fetchEmployeesErr = viewModel.fetchEmployeesErr.value,
                        fetchEmployeesErrMsg = viewModel.fetchEmployeesErrMsg.value,
                        showStartDateDialog = viewModel.showStartDateDialog.value,
                        downtimeStartDate = viewModel.pmCard.value.downtimeStartDate,
                        downtimeEndDate = viewModel.pmCard.value.downtimeEndDate,
                        showEndDateDialog = viewModel.showEndDateDialog.value,
                        downTimeStartTime = viewModel.pmCard.value.downtimeStartTime,
                        downTimeEndTime = viewModel.pmCard.value.downtimeEndTime,
                        showDownTimeStartDialog = viewModel.showStartTimeDialog.value,
                        showDownTimeEndDialog = viewModel.showEndTimeDialog.value,
                        pmCardErrorState = viewModel.pmCardErrorState.value.copy(),


                        onSelectShiftClick = {viewModel.onEvent(LogInterventionScreenEvent.OnExpandShiftDropDown)},
                        onShiftDropDownDismiss = {viewModel.onEvent(LogInterventionScreenEvent.OnShiftDropdownDismiss)},
                        onShiftClick = {shift-> viewModel.onEvent(LogInterventionScreenEvent.OnShiftClick(shift))},
                        onOtherParticipantsMenuDismiss = {viewModel.onEvent(LogInterventionScreenEvent.OnOtherParticipantsDropdownMenuDismiss)},
                        onOtherParticipantsMenuDisplay = {viewModel.onEvent(LogInterventionScreenEvent.OnExpandOtherParticipantsDropdownMenu)},
                        onParticipantClick = {participant-> viewModel.onEvent(LogInterventionScreenEvent.OnParticipantClick(participant))},
                        onRemoveParticipant = {participant-> viewModel.onEvent(LogInterventionScreenEvent.OnRemoveParticipant(participant))},
                        onStartDateDismiss = {viewModel.onEvent(LogInterventionScreenEvent.OnSelectStartDateDismiss)},
                        onSelectStartDateClick = {viewModel.onEvent(LogInterventionScreenEvent.OnSelectStartDateClick)},
                        onGetStartDate = {startDate-> viewModel.onEvent(LogInterventionScreenEvent.OnGetStartDate(startDate))},
                        onSelectEndDateClick = {viewModel.onEvent(LogInterventionScreenEvent.OnSelectEndDateClick)},
                        onEndDateDismiss = {viewModel.onEvent(LogInterventionScreenEvent.OnEndDateDialogDismiss)},
                        onGetEndDate = {endDate-> viewModel.onEvent(LogInterventionScreenEvent.OnGetEndDate(endDate))},
                        onShowDownTimeStartDialog = {viewModel.onEvent(LogInterventionScreenEvent.OnDownTimeStartDialogClick)},
                        onShowDownTimeEndDialog = {viewModel.onEvent(LogInterventionScreenEvent.OnDownTimeEndDialogClick)},
                        onGetDowntimeStartTime = {startTime-> viewModel.onEvent(LogInterventionScreenEvent.OnGetDowntimeStartTime(startTime))},
                        onGetDowntimeEndTime = {endTime-> viewModel.onEvent(LogInterventionScreenEvent.OnGetDowntimeEndTime(endTime))},
                        onStartTimeDismiss = {viewModel.onEvent(LogInterventionScreenEvent.OnDownTimeStartDismiss)},
                        onEndTimeDismiss = {viewModel.onEvent(LogInterventionScreenEvent.OnDownTimeEndDismiss)},
                        onGetTotalDowntimeDuration = {totalDowntimeDuration-> viewModel.onEvent(LogInterventionScreenEvent.OnGetTotalDowntimeDuration(totalDowntimeDuration))},
                        lineName = viewModel.pmCard.value.productionLineName,
                        equipmentName = viewModel.pmCard.value.equipmentName
                    )
                        InterventionSummarySection(
                            problemDescription = viewModel.pmCard.value.problemDescription,
                            problemDetailing = viewModel.pmCard.value.problemDetailing,
                            rootCause = viewModel.pmCard.value.rootCause,
                            observations = viewModel.pmCard.value.observations,
                            troubleshootSteps = viewModel.pmCard.value.troubleshootingSteps,
                            pmCardErrorState = viewModel.pmCardErrorState.value.copy(),
                            measuresTaken = viewModel.pmCard.value.measureTaken,
                            isInfoExpanded = viewModel.showInfo.value,
                            pmCard = viewModel.pmCard.value,

                            onProblemDescriptionChange = {problemDescr-> viewModel.onEvent(LogInterventionScreenEvent.OnProblemDescriptionChange(problemDescr))},
                            onProblemDetailingChange = {problemDetailing-> viewModel.onEvent(LogInterventionScreenEvent.OnProblemDetailingChange(problemDetailing))},
                            onRootCauseChange = {rootCause-> viewModel.onEvent(LogInterventionScreenEvent.OnRootCauseChange(rootCause))},
                            onObservationsChange = {obs-> viewModel.onEvent(LogInterventionScreenEvent.OnObservationsChange(obs))},
                            onTroubleShootStepsChange = {steps-> viewModel.onEvent(LogInterventionScreenEvent.OnTroubleshootStepsChange(steps))},
                            onMeasureTakenChange = {measuresTaken-> viewModel.onEvent(LogInterventionScreenEvent.OnMeasuresTakenChange(measuresTaken))},
                            onShowInfo = {viewModel.onEvent(LogInterventionScreenEvent.OnShowInfo)},
                            onDismissInfo = {viewModel.onEvent(LogInterventionScreenEvent.OnDismissInfo)},
                            onUriResult = {uri-> viewModel.onEvent(LogInterventionScreenEvent.OnUriResult(uri))},
                            onPhoto1Remove = {viewModel.onEvent(LogInterventionScreenEvent.OnPhoto1Delete)},
                            onPhoto2Remove = {viewModel.onEvent(LogInterventionScreenEvent.OnPhoto2Delete)},
                            onPhoto3Remove = {viewModel.onEvent(LogInterventionScreenEvent.OnPhoto3Delete)}

                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (viewModel.pmCardErrorState.value.errMsg.isNotEmpty())
                        {
                            IconTextField(
                                text = viewModel.pmCardErrorState.value.errMsg,
                                icon = Icons.Default.WarningAmber,
                                color = Color.Red,
                                iconSize =24 ,
                                textSize = 24,
                                clickEn = false
                            ) {}
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.btn_color),
                                contentColor = Color.White),
                            onClick = { viewModel.onEvent(LogInterventionScreenEvent.OnLogInterventionClick) }) {
                            Text(text = stringResource(id = R.string.log_intervention))
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                    }


            }
            }
        }
    }
}
