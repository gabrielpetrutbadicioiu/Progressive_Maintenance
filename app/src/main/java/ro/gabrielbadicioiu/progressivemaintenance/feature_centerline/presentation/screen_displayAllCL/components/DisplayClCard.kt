package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_displayAllCL.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm

@Composable
fun DisplayClCard(
    cl:CenterLineForm,
    onClClick:()->Unit
)
{
    Spacer(modifier = Modifier.height(24.dp))
    ListItem(
        headlineContent = { Text(
            text = cl.clName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )},
        leadingContent ={ Icon(
            imageVector = Icons.Outlined.Description,
            contentDescription = stringResource(id = R.string.icon_descr),
            modifier = Modifier.size(32.dp))
        },
        trailingContent = { Icon(
            imageVector = Icons.Outlined.ArrowForwardIos ,
            contentDescription =  stringResource(id = R.string.icon_descr)
        )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.btn_color),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClClick() })
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .heightIn(min = 72.dp)
//            .clickable { onClClick() },
//        colors = CardDefaults.cardColors(colorResource(id = R.color.bar_color)),
//        elevation = CardDefaults.cardElevation(8.dp)
//    ) {
//        Column(
//            modifier = Modifier.fillMaxHeight(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally) {
//            Row(modifier = Modifier
//                .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center) {
//                Text(text = cl.clName,
//                    style = MaterialTheme.typography.titleLarge,
//                    color = colorResource(id = R.color.text_color))
//            }
//        }
//
//    }
}