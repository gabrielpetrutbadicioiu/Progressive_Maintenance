package ro.gabrielbadicioiu.progressivemaintenance.core.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.BottomNavigationItem

@Composable
fun BottomNavBar(
    selectedItemIndex:Int,
    onClick:()->Unit,
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
                NavigationBarItem(
                    selected =selectedItemIndex==index ,
                    onClick = { /*TODO*/
                        onClick()
                              },
                    label = {
                        Text(text = bottomNavigationItem.title)
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
                            Icon(imageVector = if(index==selectedItemIndex) {
                                bottomNavigationItem.selectedIcon
                            } else { bottomNavigationItem.unselectedIcon},
                                contentDescription =bottomNavigationItem.title )
                        }
                    }
                )//navigationBarItem

            }
    }
}