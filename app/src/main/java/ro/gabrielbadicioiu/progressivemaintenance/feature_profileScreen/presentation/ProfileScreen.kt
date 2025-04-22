package ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.presentation

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CorporateFare
import androidx.compose.material.icons.outlined.DoneOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
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
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.PasswordRequirements

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    companyId:String,
    userId:String,
    viewModel: ProfileScreenViewModel
)
{
    val context= LocalContext.current
    val pagerState= rememberPagerState {2}
    val sheetState= rememberModalBottomSheetState()
    val profilePhotoPickerLauncher= rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia())
    {uri->
        viewModel.onEvent(ProfileScreenEvent.OnProfileUriResult(uri))

    }
    val companyLogoPhotoPickerLauncher= rememberLauncherForActivityResult(
        contract =ActivityResultContracts.PickVisualMedia() ) { uri->
        viewModel.onEvent(ProfileScreenEvent.OnCompanyLogoUriResult(uri))
    }
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ProfileScreenEvent.OnGetArgumentData(companyId = companyId, userId = userId))
        viewModel.onEvent(ProfileScreenEvent.OnGetLoggedUser)

        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is ProfileScreenViewModel.ProfileScreenUiEvent.OnNavigateToLoginScreen->{
                    navController.navigate(Screens.SignInScreen)
                }
                is ProfileScreenViewModel.ProfileScreenUiEvent.OnNavigateHome->{
                    navController.navigate(Screens.HomeScreen(companyID = companyId, userID = userId))
                }
                is ProfileScreenViewModel.ProfileScreenUiEvent.OnNavigateToMembers->{
                    navController.navigate(Screens.MembersScreenRoute(companyID = companyId, userId = userId))
                }
                is ProfileScreenViewModel.ProfileScreenUiEvent.OnNavigateToAddProductionLine->{
                    navController.navigate(Screens.AddProductionLineScreen(companyID = companyId, userID = userId))
                }
                is ProfileScreenViewModel.ProfileScreenUiEvent.OnShowToast->{
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is ProfileScreenViewModel.ProfileScreenUiEvent.OnAnimateToPage->{
                    if (!pagerState.isScrollInProgress)
                    { pagerState.animateScrollToPage(event.page)}

                }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val scrollBehavior= TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(

                    title = {
                        Row(modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            ) {
                            if (viewModel.loggedUser.value.rank!=UserRank.OWNER.name)
                            {
                                Text(text = stringResource(id = R.string.profile))
                            }
                            if (viewModel.loggedUser.value.rank==UserRank.OWNER.name && viewModel.showOtp.value)
                            {
                                Text(text = viewModel.loggedCompany.value.otp,
                                    fontWeight = FontWeight.ExtraBold)
                            }
                        }


                    },
                    //search interventions
                    actions = {
                        //show otp
                        if (viewModel.loggedUser.value.rank==UserRank.OWNER.name)
                        {
                            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                                if (!viewModel.showOtp.value)
                                {
                                    Text(text = stringResource(id = R.string.show_otp))
                                }

                                IconButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnShowOtpClick) }) {
                                    Icon(
                                        imageVector = if(viewModel.showOtp.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                        contentDescription = stringResource(id = R.string.warning_icon_descr),
                                        tint = colorResource(id = R.color.text_color)
                                    )
                                }
                            }
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
                    activeScreen = ActiveScreen.PROFILE,
                    userRank = viewModel.loggedUser.value.rank,
                    hasHomeBadge = false,
                    onHomeClick = { viewModel.onEvent(ProfileScreenEvent.OnNavigateHome)},
                    onActionBtnClick = {

                        if (viewModel.loggedUser.value.rank!= UserRank.USER.name)
                        {
                            viewModel.onEvent(ProfileScreenEvent.OnAddProductionLineClick)
                        }
                        else{
                            /*TODO*/                        }
                    },
                    onProfileScreenClick = { },
                    onMembersClick = { viewModel.onEvent(ProfileScreenEvent.OnNavigateToMembers)},
                    onStatisticsClick = { /*TODO*/  })


            },

        ) {
                innerPadding->


            LazyColumn(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top)
            {



                //profile pic
                stickyHeader {

                    Spacer(modifier = Modifier.height(50.dp))
                    Column {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            if (viewModel.selectedTab.value==0)
                            {
                                BadgedBox(
                                    badge = {
                                        if (viewModel.loggedUser.value.rank==UserRank.OWNER.name)
                                        {
                                            DisplayLottie(
                                                spec = LottieCompositionSpec.RawRes(R.raw.premium_badge),
                                                size = 45.dp)
                                        }
                                        if (viewModel.loggedUser.value.rank==UserRank.ADMIN.name)
                                        {
                                            DisplayLottie(
                                                spec = LottieCompositionSpec.RawRes(R.raw.elite_badge),
                                                size =45.dp )
                                        }
                                    }) {
                                    if (viewModel.loggedUser.value.profilePicture.isNotEmpty())
                                    {
                                        AsyncImage(model =viewModel.loggedUser.value.profilePicture,
                                            contentDescription = stringResource(id = R.string.image_description),
                                            modifier = Modifier
                                                .size(120.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop,
                                        )
                                    }
                                    else{
                                        Image(
                                            painter = painterResource(id = R.drawable.auth_image),
                                            contentDescription = stringResource(id = R.string.image_description),
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(shape = CircleShape))
                                    }

                                }//badge box
                            }
                            if (viewModel.selectedTab.value==1)
                            {
                                AsyncImage(model =viewModel.loggedCompany.value.companyLogoUrl,
                                    contentDescription = stringResource(id = R.string.image_description),
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop,
                                )
                            }


                        }//row
                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        )
                        {
                            if (viewModel.selectedTab.value==0)
                            {
                                TextButton(onClick = {
                                    profilePhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.LightGray,
                                        containerColor = Color.Transparent)) {
                                    Row()
                                    { if (viewModel.showLoadProfilePhotoProgressBar.value)
                                    {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = colorResource(id = R.color.btn_color))
                                    }
                                    else{
                                        Text(text = stringResource(id = R.string.change_profile_pic))
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Icon(
                                            imageVector = Icons.Outlined.CameraAlt ,
                                            contentDescription = stringResource(id = R.string.icon_descr))
                                    }

                                    }
                                }//profile pic txt btn
                            }

                            if(viewModel.selectedTab.value==1 && viewModel.loggedUser.value.rank==UserRank.OWNER.name)
                            {
                                TextButton(onClick = {
                                    companyLogoPhotoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.LightGray,
                                        containerColor = Color.Transparent)) {
                                    Row()
                                    { if (viewModel.showLoadProfilePhotoProgressBar.value)
                                    {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = colorResource(id = R.color.btn_color))
                                    }
                                    else{
                                        Text(text = stringResource(id = R.string.change_logo))
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Icon(
                                            imageVector = Icons.Outlined.CameraAlt ,
                                            contentDescription = stringResource(id = R.string.icon_descr))
                                    }

                                    }
                                }//company logo txt btn
                            }


                        }//row
                    }

                   
                }//sticky header profile pic
                
                stickyHeader { 
                    TabRow(selectedTabIndex = viewModel.selectedTab.value,
                        indicator ={tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[viewModel.selectedTab.value]),
                                color =  colorResource(id = R.color.btn_color)
                            )
                        }
                        ) {
                        Tab(
                            selected = viewModel.selectedTab.value==0,
                            onClick = {
                                viewModel.onEvent(ProfileScreenEvent.OnAnimateScrollToPage(0))
                                viewModel.onEvent(ProfileScreenEvent.OnUserTabClick) },
                            selectedContentColor = colorResource(id = R.color.btn_color),
                            unselectedContentColor = Color.LightGray
                            )
                        {
                            Text(text = stringResource(id = R.string.user))
                        }
                        
                        Tab(selected = viewModel.selectedTab.value==1,
                            onClick = {
                                viewModel.onEvent(ProfileScreenEvent.OnAnimateScrollToPage(1))
                                viewModel.onEvent(ProfileScreenEvent.OnCompanyTabClick) },
                            selectedContentColor = colorResource(id = R.color.btn_color),
                            unselectedContentColor = Color.LightGray) {
                            Text(text = stringResource(id = R.string.company))
                        }
                    }//tab row

                }

                item {
                    HorizontalPager(state = pagerState,
                        modifier = Modifier.fillMaxSize()) {page->
                        Column(horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier.fillMaxSize()) {
                            Spacer(modifier = Modifier.height(32.dp))
                            if (page==0)
                            {   if (!pagerState.isScrollInProgress)
                            {viewModel.onEvent(ProfileScreenEvent.OnUserTabClick)}
                                Text(
                                    text = stringResource(id = R.string.user_info),
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(start = 16.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)
                                Spacer(modifier = Modifier.height(16.dp))
                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors(
                                        unfocusedIndicatorColor = if (viewModel.isFirstNameEditing.value) Color.LightGray else Color.Transparent,
                                        focusedIndicatorColor = if (viewModel.isFirstNameEditing.value) colorResource(id = R.color.btn_color) else Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                    ),
                                    value = if (viewModel.isFirstNameEditing.value) viewModel.firstName.value else viewModel.loggedUser.value.firstName,
                                    onValueChange = {firstName-> viewModel.onEvent(ProfileScreenEvent.OnFirstNameChange(firstName))},
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.PersonOutline,
                                            contentDescription = stringResource(id = R.string.icon_descr) )
                                    },
                                    trailingIcon = {
                                        if (viewModel.isFirstNameEditing.value)
                                        {
                                            Row {
                                                IconButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnDoneEditFirstNameClick)}) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.DoneOutline,
                                                        contentDescription = stringResource(id = R.string.icon_descr),
                                                        tint = colorResource(id = R.color.btn_color))
                                                }
                                                IconButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnCancelFirstNameEditClick) }) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.Cancel,
                                                        contentDescription = stringResource(id = R.string.icon_descr),
                                                        tint = Color.Red)
                                                }
                                            }
                                        }
                                        else{
                                            IconButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnEditFirstNameClick) }) {
                                                Icon(
                                                    imageVector = Icons.Outlined.Edit,
                                                    contentDescription = stringResource(id = R.string.icon_descr) )
                                            }
                                        }

                                    },
                                    readOnly = !viewModel.isFirstNameEditing.value
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)

                                Spacer(modifier = Modifier.height(16.dp))
                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors(
                                        unfocusedIndicatorColor = if (viewModel.isLastNameEditing.value) Color.LightGray else Color.Transparent,
                                        focusedIndicatorColor = if (viewModel.isLastNameEditing.value) colorResource(id = R.color.btn_color) else Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                    ),
                                    value = if (viewModel.isLastNameEditing.value) viewModel.lastName.value else viewModel.loggedUser.value.lastName,
                                    onValueChange = {lastName-> viewModel.onEvent(ProfileScreenEvent.OnLastNameChange(lastName))},
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.PersonOutline,
                                            contentDescription = stringResource(id = R.string.icon_descr) )
                                    },
                                    trailingIcon = {

                                        if (viewModel.isLastNameEditing.value)
                                        {
                                            Row {
                                                IconButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnDoneEditLastName)}) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.DoneOutline,
                                                        contentDescription = stringResource(id = R.string.icon_descr),
                                                        tint = colorResource(id = R.color.btn_color))
                                                }
                                                IconButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnEditLastNameCancelClick) }) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.Cancel,
                                                        contentDescription = stringResource(id = R.string.icon_descr),
                                                        tint = Color.Red)
                                                }
                                            }
                                        }
                                        else{
                                            IconButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnEditLastNameClick) }) {
                                                Icon(
                                                    imageVector = Icons.Outlined.Edit,
                                                    contentDescription = stringResource(id = R.string.icon_descr) )
                                            }
                                        }
                                    },
                                    readOnly = !viewModel.isLastNameEditing.value
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)

                                Spacer(modifier = Modifier.height(16.dp))
                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors(
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                    ),
                                    value = viewModel.loggedUser.value.position,
                                    onValueChange = {},
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.WorkOutline,
                                            contentDescription = stringResource(id = R.string.icon_descr) )
                                    },
                                    readOnly = true
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)

                                Spacer(modifier = Modifier.height(16.dp))
                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors(
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                    ),
                                    value = viewModel.loggedUser.value.rank,
                                    onValueChange = {},
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.Badge,
                                            contentDescription = stringResource(id = R.string.icon_descr) )
                                    },
                                    trailingIcon = {
                                        if (viewModel.loggedUser.value.rank!=UserRank.USER.name)
                                        {
                                            val spec=if (viewModel.loggedUser.value.rank==UserRank.OWNER.name) LottieCompositionSpec.RawRes(R.raw.premium_badge) else LottieCompositionSpec.RawRes(R.raw.elite_badge)
                                            DisplayLottie(
                                                spec =spec ,
                                                size = 45.dp )
                                        }

                                    },
                                    readOnly = true
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = stringResource(id = R.string.acc_settings),
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(start = 16.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)
                                //change pass btn
                                TextButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnOpenSheet) })
                                {
                                    Row(modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start) {
                                        Text(text = stringResource(id = R.string.change_pass),
                                            color = Color.LightGray)
                                    }

                                     }
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)
                                TextButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnLogoutClick) }) {
                                    Row(modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Logout ,
                                            contentDescription = stringResource(id = R.string.icon_descr),
                                            tint = Color.Red)
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text(text = stringResource(id = R.string.logout),
                                            color = Color.Red)
                                    }
                                }

                            }//if


                            if (page==1)
                            {
                                if (!pagerState.isScrollInProgress){
                                    viewModel.onEvent(ProfileScreenEvent.OnCompanyTabClick)
                                }
                                Text(
                                    text = stringResource(id = R.string.company_info),
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(start = 16.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)

                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors(
                                        unfocusedIndicatorColor = if (viewModel.isOrganisationNameEditing.value) Color.LightGray else Color.Transparent,
                                        focusedIndicatorColor = if (viewModel.isOrganisationNameEditing.value) colorResource(id = R.color.btn_color) else Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                    ),
                                    value = if (viewModel.isOrganisationNameEditing.value) viewModel.editingOrganisationName.value else viewModel.loggedCompany.value.organisationName,
                                    onValueChange = {organisationName-> viewModel.onEvent(ProfileScreenEvent.OnOrganisationNameChange(organisationName))},
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.CorporateFare,
                                            contentDescription = stringResource(id = R.string.icon_descr) )
                                    },
                                    trailingIcon = {
                                        if (viewModel.loggedUser.value.rank==UserRank.OWNER.name)
                                        {
                                            if (viewModel.isOrganisationNameEditing.value)
                                            {
                                                Row {
                                                    IconButton(onClick = {viewModel.onEvent(ProfileScreenEvent.OnEditingOrganisationNameConfirm)}) {
                                                        Icon(
                                                            imageVector = Icons.Outlined.DoneOutline,
                                                            contentDescription = stringResource(id = R.string.icon_descr),
                                                            tint = colorResource(id = R.color.btn_color))
                                                    }
                                                    IconButton(onClick = {viewModel.onEvent(ProfileScreenEvent.OnEditOrganisationNameCancel)}) {
                                                        Icon(
                                                            imageVector = Icons.Outlined.Cancel,
                                                            contentDescription = stringResource(id = R.string.icon_descr),
                                                            tint = Color.Red)
                                                    }
                                                }
                                            }
                                            else{
                                                IconButton(onClick = {viewModel.onEvent(ProfileScreenEvent.OnEditOrganisationNameClick) }) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.Edit,
                                                        contentDescription = stringResource(id = R.string.icon_descr) )
                                                }
                                            }
                                        }


                                    },
                                    readOnly = !viewModel.isOrganisationNameEditing.value
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)
                                //domain
                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors(
                                        unfocusedIndicatorColor = if (viewModel.isOrganisationDomainEditing.value) Color.LightGray else Color.Transparent,
                                        focusedIndicatorColor = if (viewModel.isOrganisationDomainEditing.value) colorResource(id = R.color.btn_color) else Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                    ),
                                    value = if (viewModel.isOrganisationDomainEditing.value) viewModel.editingOrganisationDomain.value else viewModel.loggedCompany.value.industryType,
                                    onValueChange = {domain-> viewModel.onEvent(ProfileScreenEvent.OnOrganisationDomainChange(domain))},
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.Badge,
                                            contentDescription = stringResource(id = R.string.icon_descr) )
                                    },
                                    trailingIcon = {
                                        if (viewModel.loggedUser.value.rank==UserRank.OWNER.name)
                                        {
                                            if (viewModel.isOrganisationDomainEditing.value)
                                            {
                                                Row {
                                                    IconButton(onClick = {viewModel.onEvent(ProfileScreenEvent.OnOrganisationDomainEditConfirm)}) {
                                                        Icon(
                                                            imageVector = Icons.Outlined.DoneOutline,
                                                            contentDescription = stringResource(id = R.string.icon_descr),
                                                            tint = colorResource(id = R.color.btn_color))
                                                    }
                                                    IconButton(onClick = {viewModel.onEvent(ProfileScreenEvent.OnEditOrganisationDomainCancel)}) {
                                                        Icon(
                                                            imageVector = Icons.Outlined.Cancel,
                                                            contentDescription = stringResource(id = R.string.icon_descr),
                                                            tint = Color.Red)
                                                    }
                                                }
                                            }
                                            else{
                                                IconButton(onClick = {viewModel.onEvent(ProfileScreenEvent.OnEditOrganisationDomain) }) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.Edit,
                                                        contentDescription = stringResource(id = R.string.icon_descr) )
                                                }
                                            }
                                        }


                                    },
                                    readOnly = !viewModel.isOrganisationDomainEditing.value
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)

                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors(
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedIndicatorColor =  Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                    ),
                                    value = viewModel.loggedCompany.value.country,
                                    onValueChange = {},
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.LocationOn,
                                            contentDescription = stringResource(id = R.string.icon_descr) )
                                    },

                                    readOnly = true
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.DarkGray,
                                    thickness = 1.dp)
                                if (viewModel.loggedUser.value.rank==UserRank.OWNER.name)
                                {
                                    TextField(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = TextFieldDefaults.colors(
                                            unfocusedIndicatorColor = Color.Transparent,
                                            focusedIndicatorColor =  Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                        ),
                                        value = if (viewModel.showOtp.value) viewModel.loggedCompany.value.otp else stringResource(
                                            id = R.string.otp_hint3
                                        ),
                                        onValueChange = {},
                                        leadingIcon = {
                                            
                                            Icon(
                                                imageVector =Icons.Outlined.Lock,
                                                contentDescription = stringResource(id = R.string.icon_descr) )
                                        },
                                        trailingIcon = {
                                            IconButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnShowOtpClick) }) {
                                                Icon(
                                                    imageVector = if(viewModel.showOtp.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                                    contentDescription = stringResource(id = R.string.warning_icon_descr),
                                                    tint = colorResource(id = R.color.text_color)
                                                )
                                            }
                                        },
                                        supportingText = { Text(text = stringResource(id = R.string.otp_hint2))},

                                        readOnly = true
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    HorizontalDivider(color = Color.DarkGray,
                                        thickness = 1.dp)
                                }
                                


                            }//page 1
                        }//column
                    }
                }
            }//lazy column
        }//scaffold
        if (viewModel.isSheetOpen.value)
        {

            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { viewModel.onEvent(ProfileScreenEvent.OnCloseSheet) }) {

                Spacer(modifier = Modifier.height(16.dp))
                //old pass
                TextField(value = viewModel.oldPassInput.value,
                    onValueChange ={oldPass-> viewModel.onEvent(ProfileScreenEvent.OnOldPassChange(oldPass))},
                    placeholder = { Text(text = stringResource(id = R.string.old_pass))},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = colorResource(id = R.color.btn_color)
                    ),
                    visualTransformation = if (viewModel.showOldPass.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnShowOldPass) }) {
                            Icon(
                                imageVector = if (viewModel.showOldPass.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = stringResource(id = R.string.icon_descr) )
                        }
                    },
                    isError = viewModel.isPassChangeErr.value

                    )
                //new pass
                Spacer(modifier = Modifier.height(16.dp))
                TextField(value = viewModel.newPassInput.value,
                    onValueChange ={newPass-> if (newPass.length<=20){viewModel.onEvent(ProfileScreenEvent.OnNewPassChange(newPass))}},
                    placeholder = { Text(text = stringResource(id = R.string.new_pass))},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = colorResource(id = R.color.btn_color)
                    ),
                    visualTransformation = if (viewModel.showNewPass.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = { IconButton(onClick = {viewModel.onEvent(ProfileScreenEvent.OnShowNewPass)}) {
                        Icon(
                            imageVector = if (viewModel.showNewPass.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = stringResource(id = R.string.icon_descr) )
                    }},
                    isError = viewModel.isPassChangeErr.value
                )
                //conf new pass
                Spacer(modifier = Modifier.height(16.dp))
                TextField(value = viewModel.confNewPassInput.value,
                    onValueChange ={confNewPass-> if (confNewPass.length<=20)
                    {
                        viewModel.onEvent(ProfileScreenEvent.OnConfNewPassChange(confNewPass))
                    }
                        },
                    placeholder = { Text(text = stringResource(id = R.string.conf_new_pass))},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = colorResource(id = R.color.btn_color)
                    ),
                    visualTransformation = if (viewModel.showConfNewPass.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = { IconButton(onClick = { viewModel.onEvent(ProfileScreenEvent.OnShowConfNewPass) }) {
                        Icon(
                            imageVector = if (viewModel.showConfNewPass.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                            contentDescription = stringResource(id = R.string.icon_descr) )
                    }},
                    isError = viewModel.isPassChangeErr.value
                )
                if (viewModel.isPassChangeErr.value)
                {
                    Text(text = viewModel.passChangeErrMsg.value ,
                        color = Color.Red)

                }
                Spacer(modifier = Modifier.height(16.dp))
                PasswordRequirements(password = viewModel.newPassInput.value, confPassword = viewModel.confNewPassInput.value)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically)
                {
                    val enable=viewModel.newPassInput.value.length>8 && viewModel.newPassInput.value== viewModel.confNewPassInput.value && viewModel.newPassInput.value.isNotEmpty() &&viewModel.newPassInput.value.matches(Regex(".*[A-Z].*")) && viewModel.newPassInput.value.matches(Regex(".*\\d.*"))
                    Button(onClick = { viewModel.onEvent(ProfileScreenEvent.OnConfirmPassChange) },
                        enabled = enable,
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn_color))) {
                        Text(text = stringResource(id = R.string.confirm), color = Color.White)
                    }
                    Button(onClick = { viewModel.onEvent(ProfileScreenEvent.OnCloseSheet) },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn_color))) {
                        Text(text = stringResource(id = R.string.cancel_btn), color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }//surface
}