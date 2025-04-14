package ro.gabrielbadicioiu.progressivemaintenance.core.composables


import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Groups2
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.ActiveScreen


@Composable
fun BottomNavBar(
    activeScreen: ActiveScreen,
    userRank: String,
    hasHomeBadge:Boolean,
    onHomeClick:()->Unit,
    onActionBtnClick:()->Unit,
    onProfileScreenClick:()->Unit,
    onMembersClick:()->Unit,
    onStatisticsClick:()->Unit
)
{

    NavigationBar(containerColor = colorResource(id = R.color.bar_color)) {
        //home
        NavigationBarItem(
            selected =false,
            onClick = { onHomeClick() },
            icon = { 
                BadgedBox(badge ={
                        if (hasHomeBadge)
                        {
                            Badge()
                        }
                } ) {
                        Icon(imageVector =Icons.Outlined.Home,
                            contentDescription = stringResource(id = R.string.icon_descr),
                            tint = if (activeScreen==ActiveScreen.HOME) colorResource(id = R.color.btn_color) else Color.White,
                            modifier = Modifier.size(32.dp)
                            )
                }
            })
        //statistics
        //aici poti sa pui top 3 erori frecvente, timp de aparitie, etc acum il vad asa pe fiecare linie, dintr-un dropdown menu selectezi linia si iti da statistica
        NavigationBarItem(
            selected = activeScreen==ActiveScreen.STATISTICS,
            onClick = { onStatisticsClick() },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.BarChart,
                    contentDescription = stringResource(id = R.string.icon_descr),
                    tint = if (activeScreen==ActiveScreen.PROFILE) colorResource(id = R.color.btn_color) else Color.White,
                    modifier = Modifier.size(32.dp)
                )
            })
        //action button
        FloatingActionButton(onClick = { onActionBtnClick() },
            containerColor = if (userRank==UserRank.USER.name)colorResource(id = R.color.btn_color) else Color.Transparent,
            shape = CircleShape) {
            val spec=if (userRank==UserRank.USER.name) LottieCompositionSpec.RawRes(R.raw.ic_ai_lottie) else LottieCompositionSpec.RawRes(R.raw.ic_add_prod_line)
            DisplayLottie(spec =spec , size = 65.dp)
        }
        //members
        NavigationBarItem(selected =false,
            onClick = { onMembersClick() },
            icon = {
                Icon(imageVector = Icons.Outlined.Groups2,
                    contentDescription = stringResource(id = R.string.icon_descr),
                    tint = if (activeScreen==ActiveScreen.MEMBERS) colorResource(id = R.color.btn_color) else Color.White,
                    modifier = Modifier.size(32.dp)
                    )
            })
        //profile
        NavigationBarItem(
            selected = activeScreen==ActiveScreen.PROFILE,
            onClick = { onProfileScreenClick() },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = stringResource(id = R.string.icon_descr),
                    tint = if (activeScreen==ActiveScreen.PROFILE) colorResource(id = R.color.btn_color) else Color.White,
                    modifier = Modifier.size(32.dp)
                )
            })
    }
}