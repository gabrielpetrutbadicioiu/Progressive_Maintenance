package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails

@Composable
fun ClickableUser(participant: UserDetails,
)
{

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Avatar
            BadgedBox(
                badge = {
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
                }
            ) {
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

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "${participant.firstName} ${participant.lastName}",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
