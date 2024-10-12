package ro.gabrielbadicioiu.progressivemaintenance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass.CreatePassScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass.CreatePassViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.SignInScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.SignInViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP.EmailConfirmationScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP.OTPViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation.EmailValidationScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation.EmailValidationViewModel

import ro.gabrielbadicioiu.progressivemaintenance.ui.theme.ProgressiveMaintenanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgressiveMaintenanceTheme {
               val navController= rememberNavController()
                val signInScreenViewModel=getViewModel<SignInViewModel>()
                val emailValidationViewModel=getViewModel<EmailValidationViewModel>()
                val emailConfirmationViewModel=getViewModel<OTPViewModel>()
                val createPasswordViewmodel=getViewModel<CreatePassViewModel>()
             //   SignInScreen(viewModel =signInScreenViewModel , navController = navController)

               EmailValidationScreen(emailValidationViewModel, navController)
            //    EmailConfirmationScreen(emailConfirmationViewModel)

           //     CreatePassScreen(createPasswordViewmodel)
            }
        }
    }
}

