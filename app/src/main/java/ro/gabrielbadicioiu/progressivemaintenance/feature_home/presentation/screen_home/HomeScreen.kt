package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.ActiveScreen
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.BottomNavBar
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components.ProductionLineCard
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components.ProductionLineLottie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
    companyId:String,
    userId:String
)
{
    val context= LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(HomeScreenEvent.OnFetchArgumentData(companyID = companyId, userID = userId))
        viewModel.onEvent(HomeScreenEvent.OnFetchProductionLines)
        viewModel.onEvent(HomeScreenEvent.OnGetUserById)
        viewModel.onEvent(HomeScreenEvent.OnProductionLineListener)
        viewModel.eventFlow.collectLatest {
            event->
            when(event)
            {
                is HomeViewModel.HomeScreenUiEvent.OnAddProductionLineClick ->{
                    navController.navigate(Screens.AddProductionLineScreen(companyID = companyId, userID = userId))
                }
                is HomeViewModel.HomeScreenUiEvent.ToastMessage->{
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is HomeViewModel.HomeScreenUiEvent.OnEditBtnClick->{
                    navController.navigate(Screens.EditProdLineScreen(
                        companyId = companyId,
                        userID = userId,
                        productionLineId =event.id ))
                }
                is HomeViewModel.HomeScreenUiEvent.OnNavigateTo->{
                    navController.navigate(event.screen)
                }
                is HomeViewModel.HomeScreenUiEvent.OnMembersScreenClick->{
                    navController.navigate(Screens.MembersScreenRoute(companyID = companyId, userId = userId))
                }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val scrollBehavior=TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    navigationIcon = {

                            Row(horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { viewModel.onEvent(HomeScreenEvent.OnNavigateUp)}) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBackIosNew,
                                        contentDescription = stringResource(id = R.string.icon_descr),
                                        tint = colorResource(id = R.color.text_color))
                                }
                                Text(text = stringResource(id = R.string.SignIn_title),
                                    color = colorResource(id = R.color.text_color))
                            }


                    },
                    title = { //todo
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (scrollBehavior.state.collapsedFraction<0.5f)
                            {
                                Column {
                                    Text(text =
                                    "${stringResource(id = R.string.Hi)} ${viewModel.userDetails.value.firstName} ${viewModel.userDetails.value.lastName.firstOrNull().toString()},",
                                        fontSize = 18.sp)
                                    Text(text = stringResource(id = R.string.wbk),
                                        fontSize = 24.sp)
                                }
                                if (viewModel.userDetails.value.profilePicture.isEmpty())
                                {
                                    Image(
                                        painter = painterResource(id = R.drawable.auth_image),
                                        contentDescription = stringResource(id = R.string.image_description),
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(shape = CircleShape))
                                }
                                else{

                                    BadgedBox(badge = {
                                        Row {
                                            if (viewModel.userDetails.value.rank==UserRank.OWNER.name)
                                            {
                                                DisplayLottie(
                                                    spec = LottieCompositionSpec.RawRes(R.raw.premium_badge),
                                                    size = 25.dp)
                                            }
                                            if (viewModel.userDetails.value.rank==UserRank.ADMIN.name)
                                            {
                                                DisplayLottie(
                                                    spec = LottieCompositionSpec.RawRes(R.raw.elite_badge),
                                                    size =25.dp )
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                        }
                                    
                                    }
                                        
                                    ) {
                                        Row {
                                            AsyncImage(model =viewModel.userDetails.value.profilePicture,
                                                contentDescription = stringResource(id = R.string.image_description),
                                                modifier = Modifier
                                                    .size(65.dp)
                                                    .clip(CircleShape),
                                                contentScale = ContentScale.Crop,
                                            )
                                            Spacer(modifier = Modifier.width(26.dp))
                                        }

                                    }

                                }

                            }
                        }
                    },
                    //search interventions
                    actions = {
                       IconButton(onClick = { /*TODO implementare ecran search ca pe tiktok cu recents cu d-alea*/ }) {
                           Icon(
                               imageVector = Icons.Default.Search,
                               contentDescription = stringResource(id = R.string.icon_descr),
                               tint = colorResource(id = R.color.text_color))
                       }
                        
                        //notification bell
                        IconButton(onClick = { /*TODO*/ }) {

                            Icon(
                                imageVector = Icons.Default.NotificationsNone,
                                contentDescription = stringResource(id = R.string.warning_icon_descr),
                                tint = colorResource(id = R.color.text_color)
                            )
                        }
                    },//actions
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.bar_color),
                        scrolledContainerColor = colorResource(id = R.color.scroll_bar_color)
                    ),
                    )//topAppBar
            },//topBar
            bottomBar = {
               BottomNavBar(
                   activeScreen = ActiveScreen.HOME,
                   userRank = viewModel.userDetails.value.rank,
                   hasHomeBadge = false,
                   onHomeClick = { /*TODO*/ },
                   onActionBtnClick = {
                       if (viewModel.userDetails.value.rank!=UserRank.USER.name)
                       {
                           viewModel.onEvent(HomeScreenEvent.OnAddProductionLineClick)
                       }
                       else{
                           /*TODO*/                        }
                   },
                   onProfileScreenClick = { /*TODO*/ },
                   onMembersClick = { viewModel.onEvent(HomeScreenEvent.OnMembersScreenClick)},
                   onStatisticsClick = { /*TODO*/  })


            },
            floatingActionButton = {
                if (viewModel.userDetails.value.rank!=UserRank.USER.name)
                {
                    FloatingActionButton(
                        modifier = Modifier.padding(4.dp),
                        shape = CircleShape,
                        containerColor = colorResource(id = R.color.btn_color),
                        contentColor = colorResource(id = R.color.text_color),
                        onClick = { /*TODO*/ }) {
                        DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.ic_ai_lottie), size = 60.dp)
                    }
                }

            },
        ) {
            innerPadding->

            LazyColumn(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                if (viewModel.productionLineList.value.isEmpty())
                {
                    item { ProductionLineLottie() }
                    if (viewModel.fetchProdLineErr.value)
                        item { Text(text = viewModel.failedToFetchProdLinesErrMsg.value) }
                }
                else{
                    items(viewModel.productionLineList.value.size)
                        {index->
                                ProductionLineCard(
                                    userRank = viewModel.userDetails.value.rank,
                                    productionLine = viewModel.productionLineList.value[index],
                                    onExpandClick = {viewModel.onEvent(HomeScreenEvent.OnExpandBtnClick(viewModel.productionLineList.value[index].id)) },
                                    isExpanded = viewModel.productionLineList.value[index].isExpanded,
                                    onEditClick = {viewModel.onEvent(HomeScreenEvent.OnEditBtnClick(viewModel.productionLineList.value[index].id))}
                                )
                            }
                }
//                item {   Text(text = companyId)
//                    Text(text = userId) }
//                if (viewModel.showProgressBar.value)
//                {
//                    item {
//
//                        CircularProgressIndicator(
//                            modifier = Modifier.size(30.dp) ,
//                            color = colorResource(id = R.color.bar_color),
//
//                        )
//                    }
//                }
//                else{
//                    if (viewModel.fetchProdLineErr.value)
//                    {
//                        item {
//                            Text(text = viewModel.failedToFetchProdLinesErrMsg.value)
//                        }
//
//                    }
//                    else{
//                        if(viewModel.productionLineList.value.isEmpty())
//                        {
//                            item {EmptyScreenCard()  }
//                        }
//                        else{
//                            items(viewModel.productionLineList.value.size)
//                            {index->
//                                ProductionLineCard(
//                                    productionLine = viewModel.productionLineList.value[index],
//                                    onExpandClick = {viewModel.onEvent(HomeScreenEvent.OnExpandBtnClick(viewModel.productionLineList.value[index].id)) },
//                                    isExpanded = viewModel.productionLineList.value[index].isExpanded,
//                                    onEditClick = {viewModel.onEvent(HomeScreenEvent.OnEditBtnClick(viewModel.productionLineList.value[index].id))}
//                                )
//                            }
//
//                        }
//                    }
//
//                }



            }//lazy column





        }//scaffold
    }//surface
}