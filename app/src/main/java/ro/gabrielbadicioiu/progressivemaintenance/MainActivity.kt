package ro.gabrielbadicioiu.progressivemaintenance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.LoginScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.LoginViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_signUp.presentation.SignUpScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_signUp.presentation.components.SignUpScreenViewModel
import ro.gabrielbadicioiu.progressivemaintenance.ui.theme.ProgressiveMaintenanceTheme
import ro.gabrielbadicioiu.progressivemaintenance.util.Screens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgressiveMaintenanceTheme {
                val navController= rememberNavController()
                val loginScreenViewModel=getViewModel<LoginViewModel>()
                val signUpScreenViewModel=getViewModel<SignUpScreenViewModel>()
                

                    NavHost(
                        navController = navController,
                        startDestination =Screens.LoginScreen ) {
                        composable<Screens.LoginScreen>
                        {
                            LoginScreen(
                                navController=navController,
                                viewModel = loginScreenViewModel)
                        }
                        composable<Screens.SignUpScreen>
                        {
                            SignUpScreen(
                                navController = navController,
                                viewModel = signUpScreenViewModel)
                        }
                    }


            }
        }
    }
}

