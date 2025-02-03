package ro.gabrielbadicioiu.progressivemaintenance.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.BottomNavigationItem

@Composable
fun BottomNavBar(
    selectedItemIndex:Int,
    onClick:()->Unit,
    onAddEquipmentClick:()->Unit
)
{
    val items = listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.home_btn),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "",
            selectedIcon = Icons.Filled.Add,
            unselectedIcon = Icons.Outlined.Add,
            hasNews = false,
        ),
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        )
    )//items list

    NavigationBar(
        containerColor = colorResource(id = R.color.bar_color)
    ) {
            items.forEachIndexed{index, bottomNavigationItem->
                if (bottomNavigationItem.title.isEmpty())
                {
                    NavigationBarItem(
                        selected = false ,
                        onClick = { onAddEquipmentClick() },
                        icon = { Icon(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(colorResource(id = R.color.add_btn_color))
                                .padding(8.dp),
                            imageVector = Icons.Rounded.Add,
                            tint = colorResource(id = R.color.text_color),
                            contentDescription = stringResource(id = R.string.icon_descr)) },

                    )
                }
                else{
                    NavigationBarItem(
                        selected =selectedItemIndex==index ,
                        onClick = { /*TODO*/
                            onClick()
                        },
                        label = {
                            Text(text = bottomNavigationItem.title,
                                color = colorResource(id = R.color.text_color))
                        },

                        icon = { /*TODO*/
                            BadgedBox(
                                badge = {
                                    if (bottomNavigationItem.badgeCount!=null)
                                    {
                                        Badge{
                                            Text(text = bottomNavigationItem.badgeCount.toString())
                                        }
                                    }
                                    else{
                                        if (bottomNavigationItem.hasNews)
                                        {
                                            Badge()
                                        }
                                    }

                                })//badgeBox
                            {
                                Icon(
                                    imageVector = if(index==selectedItemIndex)
                                    {
                                        bottomNavigationItem.selectedIcon
                                    }
                                    else {
                                        bottomNavigationItem.unselectedIcon
                                    },

                                    contentDescription =bottomNavigationItem.title,
                                    tint = if(index==selectedItemIndex)
                                    {
                                        Color.Black
                                    }
                                    else {
                                        colorResource(id = R.color.text_color)
                                    })
                            }
                        }
                    )//navigationBarItem
                }


            }
    }
}