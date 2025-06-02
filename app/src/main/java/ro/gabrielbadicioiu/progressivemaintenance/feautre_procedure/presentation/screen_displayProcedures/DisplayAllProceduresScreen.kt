package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_displayProcedures



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayAllProceduresScreen(
    companyId:String,
    lineId:String,
    equipmentId:String,
    userId:String,
    viewModel: DisplayAllProceduresViewModel,
    navController: NavController
)
{
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(DisplayAllProceduresScreenEvent.OnGetArgumentData(
            companyId = companyId,
            lineId = lineId,
            equipmentId = equipmentId,
            userId = userId
        ))
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is DisplayAllProceduresViewModel.DisplayAllProceduresUiEvent.OnNavigateHome->{
                    navController.navigate(Screens.HomeScreen(companyID = companyId, userID = userId))
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
                        IconButton(onClick = { viewModel.onEvent(DisplayAllProceduresScreenEvent.OnNavigateHome) }) {
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
                            Text(text = stringResource(id = R.string.procedures))
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
                verticalArrangement = Arrangement.Center)
            {

            }
        }
    }
}
