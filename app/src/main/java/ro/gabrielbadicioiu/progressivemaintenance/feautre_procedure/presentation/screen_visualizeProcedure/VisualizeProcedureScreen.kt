package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_visualizeProcedure


import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_visualizeProcedure.composables.VisualizeProcedureGeneralInfo
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_visualizeProcedure.composables.VisualizeProcedureSteps

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizeProcedureScreen(
    companyId:String,
    lineId:String,
    equipmentId:String,
    userId:String,
    procedureId:String,
    viewModel: VisualizeProcedureScreenViewModel,
    navController: NavController
)
{
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(VisualizeProcedureScreenEvent.OnFetchArgumentData(
            userId = userId,
            companyId=companyId,
            productionLineId = lineId,
            procedureId = procedureId,
            equipmentId = equipmentId,
        ))
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is VisualizeProcedureScreenViewModel.VisualizeProcedureUiEvent.OnNavigateToDisplayAllProceduresScreen->{
                    navController.navigate(Screens.DisplayProceduresScreen(
                        companyId = companyId,
                        userId = userId,
                        productionLineId = lineId,
                        equipmentId = equipmentId
                    ))
                }
                 is VisualizeProcedureScreenViewModel.VisualizeProcedureUiEvent.OnNavigateToDisplayPhotoScreen->{
                     navController.navigate(Screens.DisplayImageScreen(event.uri))
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
                    navigationIcon = {
                        IconButton(onClick = { viewModel.onEvent(VisualizeProcedureScreenEvent.OnNavigateToDisplayAllProcedureScreen)}) {
                            Icon(
                                imageVector =Icons.Default.ArrowBackIosNew ,
                                contentDescription = stringResource(id = R.string.icon_descr))
                        }
                    },
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = R.string.procedure_details))
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
                if (viewModel.uiErrState.value.isFetchProcedureErr || viewModel.uiErrState.value.isFetchProductionLineErr || viewModel.uiErrState.value.isFetchAuthorErr)
                {
                    item {
                        DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.ic_error_lottie), size = 18.dp)
                        Text(text = viewModel.uiErrState.value.errMsg)
                    }
                }
                else{
                    item {
                        VisualizeProcedureGeneralInfo(
                            productionLine =viewModel.productionLine.value,
                            equipment =viewModel.equipment.value,
                            procedure = viewModel.procedure.value,
                            author =viewModel.author.value,
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
                        VisualizeProcedureSteps(
                            procedureStep = step,
                            index =index+1 ,
                            onPhoto1Click = { viewModel.onEvent(VisualizeProcedureScreenEvent.OnPhoto1Click(index)) },
                            onPhoto2Click = { viewModel.onEvent(VisualizeProcedureScreenEvent.OnPhoto2Click(index)) },
                            onPhoto3Click = {viewModel.onEvent(VisualizeProcedureScreenEvent.OnPhoto3Click(index))}
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = { viewModel.onEvent(VisualizeProcedureScreenEvent.OnEditBtnClick) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.btn_color),
                                contentColor = colorResource(id = R.color.text_color)
                            )
                            ) {
                            Text(text = stringResource(id = R.string.edit))
                        }
                    }
                }
            }
        }
    }
}
