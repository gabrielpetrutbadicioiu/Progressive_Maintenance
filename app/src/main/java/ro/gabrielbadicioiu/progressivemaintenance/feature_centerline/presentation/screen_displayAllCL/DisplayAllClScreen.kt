package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_displayAllCL

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.ArrowBackIosNew
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.Divider
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.SearchField
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.composables.SearchArea
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_displayAllCL.components.DisplayClCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DisplayAllClScreen(
    viewModel: DisplayAllClViewModel,
    navController: NavController,
    companyId:String,
    userId:String,
    productionLineId: String,
    equipmentId: String,
    )
{
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(DisplayAllClScreenEvent.OnGetArgumentData(
            companyId = companyId,
            userId = userId,
            productionLineId=productionLineId,
            equipmentId = equipmentId
        ))
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is DisplayAllClViewModel.DisplayAllClUiEvent.OnNavigateHome->{
                    navController.navigate(Screens.HomeScreen(companyID = companyId, userID = userId))
                }
                is DisplayAllClViewModel.DisplayAllClUiEvent.OnClClick->{
                    navController.navigate(Screens.CreateCenterLineScreen(
                        companyId = companyId,
                        isCreatingNewCl = false,
                        productionLineId = productionLineId,
                        userId = userId,
                        equipmentId = equipmentId,
                        clId = event.clId
                    ))
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
                        IconButton(onClick = { viewModel.onEvent(DisplayAllClScreenEvent.OnNavigateHome) })
                        {
                            Icon(
                                imageVector =Icons.Outlined.ArrowBackIosNew ,
                                contentDescription = stringResource(id = R.string.icon_descr) )
                        }
                    },
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center)
                            {
                                Text(text = stringResource(id = R.string.cl_records))
                            }
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

                if (viewModel.errState.value.isFetchClErr)
                {
                    item { 
                        DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.error), size = 128.dp)
                        IconTextField(
                            text = viewModel.errState.value.errMsg,
                            icon = Icons.Default.Warning,
                            color = Color.Red ,
                            iconSize = 32,
                            textSize =24 ,
                            clickEn = false
                        ) {}
                    }
                }
                else{
                    stickyHeader { 
                        Spacer(modifier = Modifier.height(36.dp))
                        SearchField(query = viewModel.query.value,
                        onQueryChange = {query-> viewModel.onEvent(DisplayAllClScreenEvent.OnQueryChange(query.replaceFirstChar { char-> char.uppercase() })) })
                    Divider(space = 36.dp, thickness = 1.dp, color = Color.DarkGray)}
                    if (viewModel.equipmentClList.value.isEmpty())
                    {
                        item {
                            DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.ghost_lottie), size = 128.dp)
                            Text(text = stringResource(id = R.string.empty_cl_err))
                        }
                    }
                    else{
                        itemsIndexed(viewModel.equipmentClList.value){index, cl->
                            DisplayClCard(
                                cl = cl,
                                onClClick ={viewModel.onEvent(DisplayAllClScreenEvent.OnClClick(clIndex = index))} )
                        }
                    }
                }
            }
        }
    }
}



