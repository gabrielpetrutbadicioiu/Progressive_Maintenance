package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail.Composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun ExpandableText(
    shortText:String,
    fullText:String,
    isExpanded:Boolean,
    onExpandClick:()->Unit
)
{
Column(modifier = Modifier.wrapContentSize(),
    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
    Text(text = if(isExpanded) fullText else shortText,
        fontSize = 12.sp)
    TextButton(onClick = { onExpandClick() }) {
        Text(text = if (isExpanded) stringResource(id = R.string.read_less) else stringResource(id = R.string.read_more),
            fontSize = 10.sp,
            color = colorResource(id = R.color.btn_color))
    }
}
}