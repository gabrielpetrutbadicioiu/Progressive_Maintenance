package ro.gabrielbadicioiu.progressivemaintenance.feature_signUp.presentation


import android.widget.Space
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.components.EmailTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.components.PasswordTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_signUp.presentation.components.ConfirmPasswordTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_signUp.presentation.components.SignUpButton
import ro.gabrielbadicioiu.progressivemaintenance.util.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController
)
{
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.SignUp_title),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.width(20.dp))
                    }//row
                },//title
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Screens.LoginScreen) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.NavIcon_descr))
                    }
                },//navIcon
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary))
        }//topbar
    ) {
        innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = stringResource(id = R.string.image_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp)
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))
            EmailTextField(value = "") {
                /*TODO*/
            }
            PasswordTextField(value = "") {
                /*TODO*/

            }
            ConfirmPasswordTextField(value = "") {
                /*TODO*/
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))
            SignUpButton {
                /*TODO*/
            }


        }//column
    }//scaffold
}