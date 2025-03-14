package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun CompanyDescription(
    companyName:String,
    avatar:String,
    onClick:()->Unit
)
{
    Column {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            if (avatar.isEmpty())
            {
                Image(
                    painter = painterResource(id = R.drawable.ic_unknown_company_logo),
                    contentDescription = stringResource(id = R.string.image_description),
                    modifier = Modifier
                        .size(64.dp)
                )
            }
            else{
                AsyncImage(
                    model = avatar,
                    contentDescription = stringResource(id = R.string.image_description),
                    modifier = Modifier
                        .size(64.dp),
                    //.clip(CircleShape),
                    //  contentScale = ContentScale.Crop
                )
            }
            Text(
                text = companyName,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        onClick()
                    })
        }
        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier.padding(4.dp),
            color = colorResource(id = R.color.unfocused_color)
        )

    }

}