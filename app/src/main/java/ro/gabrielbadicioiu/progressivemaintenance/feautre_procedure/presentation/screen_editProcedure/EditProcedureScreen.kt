package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_editProcedure


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_createProcedure.CreateProcedureEvent
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_createProcedure.CreateProcedureViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_createProcedure.components.CreateProcedureGeneralInfo
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_createProcedure.components.CreateProcedureSteps

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProcedureScreen(
    companyId:String,
    userId:String,
    productionLineId: String,
    equipmentId: String,
    procedureId:String,
    viewModel: EditProcedureScreenViewModel,
    navController: NavController
)
{
 val context= LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(EditProcedureScreenEvent.OnFetchArgumentData(
            companyId = companyId,
            userId=userId,
            procedureId = procedureId,
            productionLineId = productionLineId,
            equipmentId = equipmentId
        ))
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is EditProcedureScreenViewModel.EditProcedureUiEvent.OnNavigateToVisualizeProcedure->{
                    navController.navigate(Screens.VisualizeProcedureScreen(
                        companyId = companyId,
                        equipmentId = equipmentId,
                        procedureId = procedureId,
                        productionLineId = productionLineId,
                        userId = userId
                    ))
                }
                is EditProcedureScreenViewModel.EditProcedureUiEvent.ShowToast->{ Toast.makeText(context, event.message, Toast.LENGTH_LONG).show() }

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
                    navigationIcon = {
                        IconButton(onClick = {viewModel.onEvent(EditProcedureScreenEvent.OnNavigateToVisualizeProcedure)}) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBackIosNew,
                                contentDescription = stringResource(id = R.string.icon_descr))
                        }
                    },
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = R.string.edit_procedure))
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
            {
                if (viewModel.uiErrState.value.isFetchProcedureErr || viewModel.uiErrState.value.isFetchAuthorErr)
                {
                    item {  DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.error), size = 128.dp)
                        Text(text =viewModel.uiErrState.value.errMsg )}
                }
                else{
                    item {
                        CreateProcedureGeneralInfo(
                            productionLine = viewModel.productionLine.value,
                            equipment = viewModel.equipment.value,
                            date = viewModel.procedure.value.date,
                            author =viewModel.author.value,
                            isErr =viewModel.uiErrState.value.isEmptyProcedureNameErr ,
                            procedureName = viewModel.procedure.value.procedureName,
                            onProcedureNameChange = {procedureName->
                                viewModel.onEvent(EditProcedureScreenEvent.OnProcedureNameChange(procedureName.replaceFirstChar { char-> char.uppercase() }))
                            }
                        )
                    }

                    item { Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.procedure_steps),
                                color = colorResource(id = R.color.text_color),
                                fontWeight = FontWeight.ExtraBold
                            )
                        } }

                    itemsIndexed(viewModel.procedure.value.steps)
                    {index, step->
                        Spacer(modifier = Modifier.height(16.dp))
                        CreateProcedureSteps(
                            procedureStep = step,
                            onDescriptionChange = { descr ->
                                viewModel.onEvent(
                                    EditProcedureScreenEvent.OnStepDescrChange(
                                        description = descr.replaceFirstChar { char -> char.uppercase() },
                                        index = index)
                                )
                            },
                            isStepErr = viewModel.uiErrState.value.isStepDescriptionErr,
                            index =index,
                            procedureName = viewModel.procedure.value.procedureName,
                            onPhotoUriResult = {localUri-> viewModel.onEvent(EditProcedureScreenEvent.OnUriResult(localUri = localUri, index = index))},
                            onPhoto1Remove = {viewModel.onEvent(EditProcedureScreenEvent.OnPhoto1Delete(index = index))},
                            onPhoto2Remove = {viewModel.onEvent(EditProcedureScreenEvent.OnPhoto2Delete(index = index))},
                            onPhoto3Remove = {viewModel.onEvent(EditProcedureScreenEvent.OnPhoto3Delete(index = index))},
                            onDeleteStepClick = {viewModel.onEvent(EditProcedureScreenEvent.OnDeleteProcedureStep(index=index))}
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            colors = ButtonDefaults.buttonColors(contentColor = colorResource(id = R.color.btn_color),
                                containerColor = Color.Transparent),
                            onClick = { viewModel.onEvent(EditProcedureScreenEvent.OnAddNewStep) },
                            border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.btn_color))
                        ) {
                            Text(text = stringResource(id = R.string.add_step))
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            OutlinedButton(
                                colors = ButtonDefaults.buttonColors(contentColor = colorResource(id = R.color.btn_color),
                                    containerColor = Color.Transparent),
                                onClick = { viewModel.onEvent(EditProcedureScreenEvent.OnCancelBtnClick) },
                                border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.btn_color))
                            ) {
                                Text(text = stringResource(id = R.string.cancel_btn))
                            }
                            Button(
                                onClick = { viewModel.onEvent(EditProcedureScreenEvent.OnSaveBtnClick)},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.btn_color),
                                    contentColor = colorResource(id = R.color.text_color))
                            ) {
                                Text(text = stringResource(id = R.string.save))
                            }

                        }

                    }

                }

            }
        }
    }
}