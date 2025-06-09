package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_displayProcedures.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure

@Composable
fun ProcedureListItem(
    procedure: Procedure,
    onProcedureClick:()->Unit
)
{  Spacer(modifier = Modifier.height(24.dp))
    ListItem(
        headlineContent = { Text(
            text = procedure.procedureName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
            )},
        leadingContent ={ Icon(
            imageVector = Icons.Outlined.Description,
            contentDescription = stringResource(id = R.string.icon_descr),
            modifier = Modifier.size(32.dp))},
        trailingContent = { Icon(
            imageVector =Icons.Outlined.ArrowForwardIos ,
            contentDescription =  stringResource(id = R.string.icon_descr))},
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.btn_color),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onProcedureClick() })
}