package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun ProductionLineLottie()
{
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ic_prod_line))
    val progress by animateLottieCompositionAsState(
        isPlaying = true,
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(200.dp)
        ){
            LottieAnimation(
                composition=composition,
                progress = progress)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(id = R.string.empty_screen_message))
    }
    
}