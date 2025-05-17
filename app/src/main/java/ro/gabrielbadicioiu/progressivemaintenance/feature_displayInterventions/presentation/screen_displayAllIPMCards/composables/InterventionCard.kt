package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.presentation.screen_displayAllIPMCards.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardDoubleArrowDown
import androidx.compose.material.icons.outlined.KeyboardDoubleArrowUp
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.Divider
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

@Composable
fun InterventionCard(
    pmCard: ProgressiveMaintenanceCard,
    isExpanded:Boolean,
    onExpandClick:()->Unit,
    onViewDetailsClick:()->Unit
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.bar_color)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                Button(
                    onClick = { onViewDetailsClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.btn_color),
                        contentColor = Color.White)) {
                    Text(text = stringResource(id = R.string.view_details))
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text =pmCard.productionLineName ,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(id = R.color.text_color)
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text =pmCard.equipmentName ,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(id = R.color.text_color)
                )

            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {
                Text(
                    text =pmCard.problemDescription ,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(id = R.color.text_color)
                )
            }
            if (isExpanded)
            {
                Divider(space = 4.dp, thickness = 1.dp, color = Color.LightGray)
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start) {
                    Text(text = "${stringResource(id = R.string.created_by)} ${pmCard.authorName}")
                    Spacer(modifier = Modifier.width(6.dp))
                    if(pmCard.authorAvatar.isNotEmpty())
                    {
                        BadgedBox(badge = {
                            if (pmCard.authorRank!=UserRank.USER.name)
                            {
                                val spec=if (pmCard.authorRank==UserRank.OWNER.name) LottieCompositionSpec.RawRes(R.raw.elite_badge) else LottieCompositionSpec.RawRes(R.raw.premium_badge)
                                DisplayLottie(spec = spec, size =16.dp )
                            }

                        }) {
                            AsyncImage(
                                model = pmCard.authorAvatar,
                                contentDescription = stringResource(id = R.string.image_description),
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(shape = CircleShape),
                                clipToBounds = true,)

                        }
                    }
                }//author row

                Divider(space = 4.dp, thickness = 1.dp, color = Color.LightGray)
                Text(text = "${stringResource(id = R.string.intervention_start)} ${pmCard.downtimeStartDate} ${pmCard.downtimeStartTime}")
                Divider(space = 4.dp, thickness = 1.dp, color = Color.LightGray)
                Text(text = "${stringResource(id = R.string.intervention_end)} ${pmCard.downtimeEndDate} ${pmCard.downtimeEndTime}")
                Divider(space = 4.dp, thickness = 1.dp, color = Color.LightGray)
                Text(text = "${stringResource(id = R.string.total_downtime_duration)} ${pmCard.totalDowntimeDuration}")
                Divider(space = 4.dp, thickness = 1.dp, color = Color.LightGray)
                Text(text = "${stringResource(id = R.string.shift)}: ${pmCard.shift}")
                Divider(space = 4.dp, thickness = 1.dp, color = Color.LightGray)
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    if (pmCard.resolved)
                    {
                        Text(text = stringResource(id = R.string.resolved_pf))
                        Spacer(modifier = Modifier.width(4.dp))
                        DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.done_lottie), size = 60.dp)
                    }
                    else{
                        Text(text = stringResource(id = R.string.unresolved_pf))
                        Spacer(modifier = Modifier.width(4.dp))
                        DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.sad_x), size = 40.dp)
                    }
                }
            }//isExpanded
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { onExpandClick()}) {
                    Icon(
                        imageVector = if (isExpanded)Icons.Outlined.KeyboardDoubleArrowUp else Icons.Outlined.KeyboardDoubleArrowDown ,
                        contentDescription = stringResource(id = R.string.icon_descr),
                        tint = colorResource(id = R.color.btn_color))
                }
            }
        }
    }
}