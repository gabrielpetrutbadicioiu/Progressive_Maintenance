package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_createCL


import android.os.Build
import android.widget.Space
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Add
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
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_createCL.composables.CenterLineParameters
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_createCL.composables.GeneralClInfo
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCenterLineScreen(
    companyId:String,
    userId:String,
    productionLineId:String,
    equipmentId:String,
    isCreatingNewCl:Boolean,
    viewModel: CreateClViewModel,
    navController: NavController
)
{
    val context= LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(CreateCenterLineEvent.OnGetArgumentData(
            companyId = companyId,
            userId=userId,
            lineId = productionLineId,
            equipmentId = equipmentId,
            isCreatingNewCl = isCreatingNewCl
        ))
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is CreateClViewModel.CreateClUiEvent.OnNavigateToHome->{
                    navController.navigate(Screens.HomeScreen(companyID = companyId, userID = userId))
                }
                is CreateClViewModel.CreateClUiEvent.OnShowToast->{
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
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
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = R.string.create_cl))
                        }
                    },
                    navigationIcon = { IconButton(onClick = {viewModel.onEvent(CreateCenterLineEvent.OnNavigateHome)  }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = stringResource(id = R.string.icon_descr))
                    }},
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
                if (viewModel.errorState.value.isFetchDataErr)
                {
                    item {  DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.error), size = 128.dp)
                        Text(text =viewModel.errorState.value.errMsg )}

                }else{
                    item { GeneralClInfo(
                        productionLine =viewModel.prodLine.value,
                        equipment = viewModel.equipment?:Equipment(),
                        date =viewModel.date,
                        author = viewModel.currentUser.value,
                        clName = viewModel.clForm.value.clName,
                        onClNameChange = {clName-> viewModel.onEvent(CreateCenterLineEvent.OnClNameChange(clName = clName.replaceFirstChar { char-> char.uppercase() }))},
                        isNameErr = viewModel.errorState.value.isClNameErr) }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.centerlie_parameters),
                                color = colorResource(id = R.color.text_color),
                                fontWeight = FontWeight.ExtraBold
                            )
                        } }

                    itemsIndexed(viewModel.clForm.value.clParameterList)
                    {index,clParameter->
                        CenterLineParameters(
                            clParameter = clParameter,
                            onParameterNameChange = { parameterName, _ -> viewModel.onEvent(CreateCenterLineEvent.OnParameterNameChange(parameterName = parameterName, index = index)) },
                            onParameterValueChange = { parameterValue, _ -> viewModel.onEvent(CreateCenterLineEvent.OnParameterValueChange(parameterValue = parameterValue, index = index)) },
                            showDeleteBtn = isCreatingNewCl && index!=0,
                            onDeleteClick = {viewModel.onEvent(CreateCenterLineEvent.OnParameterDelete(index))},
                            isParameterErr=viewModel.errorState.value.isParameterErr && index==0
                        )

                    }//items
                    if (isCreatingNewCl)
                    {
                        item {
                            OutlinedButton(
                                onClick = { viewModel.onEvent(CreateCenterLineEvent.OnAddParameterClick) },
                                border = BorderStroke(1.dp, color = colorResource(id = R.color.btn_color)),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = colorResource(id = R.color.btn_color),
                                )
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = stringResource(id = R.string.image_description))
                                    Text(text = stringResource(id = R.string.add_parameter) )
                                }

                            }
                        }
                    }
                    item {
                        if (isCreatingNewCl)
                        {
                            Spacer(modifier = Modifier.height(32.dp))
                            Button(
                                onClick = { viewModel.onEvent(CreateCenterLineEvent.OnSaveClick) },
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
