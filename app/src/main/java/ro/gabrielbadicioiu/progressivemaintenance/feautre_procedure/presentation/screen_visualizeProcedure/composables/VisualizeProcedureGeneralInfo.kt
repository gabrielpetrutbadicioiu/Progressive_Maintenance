package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_visualizeProcedure.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.Divider
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.Equipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.model.ProductionLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.composables.DisplayUserWithAvatar
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure

@Composable
fun VisualizeProcedureGeneralInfo(
    productionLine: ProductionLine,
    equipment: Equipment,
    author:UserDetails,
    procedure:Procedure
)
{
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.general_info),
            color = colorResource(id = R.color.text_color),
            fontWeight = FontWeight.ExtraBold
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text =" ${stringResource(id = R.string.line_name)} ${productionLine.lineName}",
            color = colorResource(id = R.color.text_color),
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text =" ${stringResource(id = R.string.equipment)} ${equipment.name}",
            color = colorResource(id = R.color.text_color),
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text =" ${stringResource(id = R.string.created_on)} ${procedure.date}",
            color = colorResource(id = R.color.text_color),
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text =" ${stringResource(id = R.string.created_by)}",
            color = colorResource(id = R.color.text_color),
        )
        DisplayUserWithAvatar(
            participant =author,
            modifier = Modifier
        )
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        value =procedure.procedureName,
        readOnly =true ,
        onValueChange ={},
        supportingText = { Text(text = stringResource(id = R.string.procedure_title))},
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colorResource(id = R.color.btn_color))
    )
    Divider(space = 8.dp, thickness =2.dp, color = Color.DarkGray)

}