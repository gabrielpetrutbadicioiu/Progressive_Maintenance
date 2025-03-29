package ro.gabrielbadicioiu.progressivemaintenance.core.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun DisplayLottie(
    spec: LottieCompositionSpec.RawRes,
    size:Dp
)
{
    val composition by rememberLottieComposition(spec =spec )
    val progress by animateLottieCompositionAsState(
        composition =composition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever)
    Box(modifier = Modifier.size(size)) {
        LottieAnimation(composition = composition, progress = progress)
    }
}