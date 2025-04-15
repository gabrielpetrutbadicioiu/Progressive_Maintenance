package ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails

@Composable
fun MemberCard(
    user: UserDetails,
    currentUser:UserDetails,
    isDropdownMenuExpanded:Boolean,
    onShowDropdownMenu:()->Unit,
    onDismissDropdownMenu:()->Unit,
    onChangeUserRank:(rank:UserRank)->Unit,
    onEditPositionClick:()->Unit,
    onBanClick:()->Unit,
    onUnbanClick:()->Unit
)
{
    Card(modifier = Modifier.wrapContentSize(),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bar_color))) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                BadgedBox(badge = {
                    if (user.rank==UserRank.OWNER.name)
                    {
                        DisplayLottie(
                            spec = LottieCompositionSpec.RawRes(R.raw.premium_badge),
                            size = 25.dp)
                    }
                    if (user.rank==UserRank.ADMIN.name)
                    {
                        DisplayLottie(
                            spec = LottieCompositionSpec.RawRes(R.raw.elite_badge),
                            size =25.dp )
                    }
                }) {
                    if (user.profilePicture.isNotEmpty())
                    {
                        AsyncImage(model =user.profilePicture,
                            contentDescription = stringResource(id = R.string.image_description),
                            modifier = Modifier
                                .size(65.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop)
                    }
                    else{
                        Image(
                            painter = painterResource(id = R.drawable.auth_image),
                            contentDescription = stringResource(id = R.string.image_description),
                            modifier = Modifier
                                .size(80.dp)
                                .clip(shape = CircleShape))
                    }

                }

                Text(
                    text = "${user.firstName} ${user.lastName}",
                    color = Color.White,
                    fontSize = 18.sp
                )
                if (currentUser.rank==UserRank.OWNER.name && user.rank!=UserRank.OWNER.name)
                {
                    IconButton(onClick = { onShowDropdownMenu() }) {
                        Icon(imageVector = Icons.Default.MoreVert ,
                            contentDescription = stringResource(id = R.string.icon_descr),
                            tint = colorResource(id = R.color.btn_color))

                        DropdownMenu(
                            expanded = isDropdownMenuExpanded ,
                            onDismissRequest = { onDismissDropdownMenu() }) {
                            if(!user.hasBeenBanned) {
                                if (user.rank == UserRank.USER.name) {
                                    DropdownMenuItem(
                                        text = { Text(text = stringResource(id = R.string.promote_admin)) },
                                        onClick = { onChangeUserRank(UserRank.ADMIN) })
                                }

                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.promote_owner)) },
                                    onClick = { onChangeUserRank(UserRank.OWNER) })

                                if (user.rank == UserRank.ADMIN.name) {
                                    DropdownMenuItem(
                                        text = { Text(text = stringResource(id = R.string.demote)) },
                                        onClick = { onChangeUserRank(UserRank.USER) })
                                }

                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.update_position)) },
                                    onClick = { onEditPositionClick() })

                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.Ban)) },
                                    onClick = { onBanClick() })
                            }
                          else
                            {
                                DropdownMenuItem(
                                    text = { Text(text = stringResource(id = R.string.unban)) },
                                    onClick = {onUnbanClick()})
                            }


                        }

                    }
                }
                else{
                    Spacer(modifier = Modifier.width(8.dp))
                }

            }//row
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color.LightGray,
                thickness = 2.dp)

            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Position:")
                Spacer(modifier = Modifier.width(24.dp))
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)){
                    Text(text = user.position)
                    Spacer(modifier = Modifier.width(8.dp))
                }

            }
            HorizontalDivider(color = Color.LightGray,
                thickness = 2.dp)
            //email
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Email:")
                Spacer(modifier = Modifier.width(24.dp))
                Row(modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = user.email)
                    Spacer(modifier = Modifier.width(8.dp))
                }

            }
            HorizontalDivider(color = Color.LightGray,
                thickness = 2.dp)
            //rank
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Rank:")
                Spacer(modifier = Modifier.width(24.dp))
                Row(modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = user.rank)
                }
                if (user.rank==UserRank.OWNER.name)
                {
                    DisplayLottie(
                        spec = LottieCompositionSpec.RawRes(R.raw.premium_badge),
                        size = 40.dp)
                }
                if (user.rank==UserRank.ADMIN.name)
                {
                    DisplayLottie(
                        spec = LottieCompositionSpec.RawRes(R.raw.elite_badge),
                        size =40.dp )
                }

            }
        }
    }
}