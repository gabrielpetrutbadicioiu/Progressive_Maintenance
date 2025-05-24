package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails

@Composable
fun DisplayUserWithAvatar(
    participant: UserDetails,
    modifier: Modifier
) {
    Row(horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
        )
    {
        // Author name
        TextField(
            value = "${participant.firstName} ${participant.lastName}",
            onValueChange = {},
            readOnly = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            trailingIcon = {
                // Profile and badge
                BadgedBox(badge = {
                    when (participant.rank) {
                        UserRank.OWNER.name -> DisplayLottie(
                            spec = LottieCompositionSpec.RawRes(R.raw.premium_badge),
                            size = 20.dp
                        )

                        UserRank.ADMIN.name -> DisplayLottie(
                            spec = LottieCompositionSpec.RawRes(R.raw.elite_badge),
                            size = 20.dp
                        )
                    }
                }) {
                    if (participant.profilePicture.isNotEmpty()) {
                        AsyncImage(
                            model = participant.profilePicture,
                            contentDescription = stringResource(id = R.string.image_description),
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        DisplayLottie(
                            spec = LottieCompositionSpec.RawRes(R.raw.auth_lottie),
                            size = 48.dp
                        )
                    }
                }

            }
        )


    }

}
