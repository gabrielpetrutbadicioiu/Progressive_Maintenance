package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_userName
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Boy
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.EnButton
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.util.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_userName.components.NameTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameScreen(
    viewModel: UserNameViewModel,
    navController: NavController
)
{
    LaunchedEffect(key1 = true)
    {
      viewModel.eventFlow.collectLatest {
          event->
          when(event){
              is UserNameViewModel.UserNameUiEvent.OnBackBtnClick->{
                  navController.navigateUp()
              }
              is UserNameViewModel.UserNameUiEvent.OnFinishBtnClick->{
                     navController.navigate(Screens.SignInScreen)
              }
          }
      }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.SignUp_title),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onEvent(UserNameEvent.OnBackBtnClick)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = stringResource(
                                id = R.string.NavIcon_descr
                            )
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary))

        }

    ) {
            innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = stringResource(
                    id = R.string.image_description
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp))

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            NameTextField(value = viewModel.firstName,
                onValueChange ={
                    firstName->
                    viewModel.onEvent(UserNameEvent.FirstNameInput(firstName))
                } ,
                label = viewModel.firstNameLabel,
                icon =Icons.Default.Boy,
                isError = viewModel.firstNameErr)
            NameTextField(value = viewModel.lastName,
                onValueChange ={
                    lastName->
                    viewModel.onEvent(UserNameEvent.LastNameInput(lastName))
                } ,
                label = viewModel.lastNameLabel,
                icon =Icons.Default.Boy ,
                isError = viewModel.lastNameErr)

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
            EnButton(
                onButtonClick = {
                    viewModel.onEvent(UserNameEvent.OnFinishBtnClick)
                },
                btnEnabled = viewModel.lastName.isNotBlank()&& viewModel.firstName.isNotBlank() &&!viewModel.firstNameErr && !viewModel.lastNameErr,
                text = stringResource(id = R.string.finish_btn) )

        }

    }

}