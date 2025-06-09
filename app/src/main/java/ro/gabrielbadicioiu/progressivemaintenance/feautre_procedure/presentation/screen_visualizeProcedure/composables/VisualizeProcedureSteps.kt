package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_visualizeProcedure.composables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.ProcedureStep


@Composable
fun VisualizeProcedureSteps(
    procedureStep: ProcedureStep,
    index:Int,
    onPhoto1Click:()->Unit,
    onPhoto2Click:()->Unit,
    onPhoto3Click:()->Unit
)
{


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        value = procedureStep.stepDescription,
        onValueChange = {},
        isError =false,
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colorResource(id = R.color.btn_color)),
        leadingIcon = { Text(text = index.toString()) },
        readOnly = true
    )
    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {
        val imageSize=64.dp

        if (procedureStep.photo1Uri.isNotEmpty())
        {
                AsyncImage(
                    model = procedureStep.photo1Uri,
                    contentDescription = stringResource(id = R.string.image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(imageSize)
                        .clickable {onPhoto1Click()})
        }
        if (procedureStep.photo2Uri.isNotEmpty())
        {

                AsyncImage(
                    model = procedureStep.photo2Uri,
                    contentDescription = stringResource(id = R.string.image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(imageSize)
                        .clickable { onPhoto2Click() })
        }
        if (procedureStep.photo3Uri.isNotEmpty())
        {

                AsyncImage(
                    model =procedureStep.photo3Uri ,
                    contentDescription = stringResource(id = R.string.image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(imageSize)
                        .clickable { onPhoto3Click()})
        }
    }
}