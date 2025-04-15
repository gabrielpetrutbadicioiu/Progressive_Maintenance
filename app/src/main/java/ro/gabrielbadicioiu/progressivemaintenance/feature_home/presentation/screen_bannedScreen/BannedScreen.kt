package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_bannedScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun BannedScreen()
{
    BackHandler {

    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            innerPadding->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Icon(
                    imageVector = Icons.Default.Block,
                    contentDescription = stringResource(id = R.string.icon_descr),
                    modifier = Modifier.size(128.dp),
                    tint = Color.Red
                    )
                Text(text = stringResource(id = R.string.access_suspended),
                    color = Color.Red,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(16.dp))

                    Text(text = stringResource(id = R.string.disabled_acc),
                        color = Color.Red)


                
            }
        }
    }
}