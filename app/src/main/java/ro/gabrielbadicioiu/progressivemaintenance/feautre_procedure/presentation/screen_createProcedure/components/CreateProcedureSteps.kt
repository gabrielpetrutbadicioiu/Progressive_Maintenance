package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_createProcedure.components

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.ProcedureStep

@Composable
fun CreateProcedureSteps(
procedureStep: ProcedureStep,
procedureName:String,
onDescriptionChange:(String)->Unit,
isStepErr:Boolean,
index:Int,
onPhotoUriResult:(String)->Unit,
onPhoto1Remove:()->Unit,
onPhoto2Remove:()->Unit,
onPhoto3Remove:()->Unit,
onDeleteStepClick:()->Unit
)
{
    val photoPickerLauncher= rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {uri->
        onPhotoUriResult(uri.toString())
    }
    val context= LocalContext.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        value = procedureStep.stepDescription,
        onValueChange = {stepDescr->onDescriptionChange(stepDescr)},
        isError =isStepErr,
        placeholder = { Text(text = stringResource(id = R.string.procedure_step_ph))},
        supportingText = {
            if (index==0)
            {
                Text(text = stringResource(id = R.string.procedure_step_st))
            }
            else{
                TextButton(onClick = { onDeleteStepClick() }) {
                    Text(text = stringResource(id = R.string.delete),
                        color = Color.Red)
                }
            }

                         },
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colorResource(id = R.color.btn_color)),
        leadingIcon = { Text(text = (index+1).toString())},
        trailingIcon = { IconButton(onClick = {
            if (procedureStep.photo1Uri.isEmpty()||procedureStep.photo2Uri.isEmpty()||procedureStep.photo3Uri.isEmpty())
            {
                if (procedureName.isEmpty()||procedureStep.stepDescription.isEmpty())
                {
                    Toast.makeText(context, "Enter procedure name and step description first", Toast.LENGTH_LONG).show()
                }
                else{
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
           }
            else{

                Toast.makeText(context,"You can add up to 3 photos per step." , Toast.LENGTH_LONG).show()
            }
        }) {
            Icon(
                imageVector = Icons.Outlined.AddAPhoto,
                contentDescription = stringResource(id = R.string.icon_descr))
        }}
    )
    if (procedureStep.photo1Uri.isEmpty()&&procedureStep.photo2Uri.isEmpty()&&procedureStep.photo3Uri.isEmpty() && index==0)
    {Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start)
    {
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = stringResource(id = R.string.icon_descr),
            Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(id = R.string.tap_hint),
            fontSize = 10.sp)
    }

    }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {
        val imageSize=64.dp

        if (procedureStep.photo1Uri.isNotEmpty())
        {
            BadgedBox(badge = {
                IconButton(onClick = { onPhoto1Remove() }) {
                    Icon(
                        imageVector = Icons.Outlined.Remove,
                        contentDescription = stringResource(id = R.string.icon_descr),
                        tint = Color.Red)
                }
            }) {
                AsyncImage(
                    model = procedureStep.photo1Uri,
                    contentDescription = stringResource(id = R.string.image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(imageSize))
            }

        }
        if (procedureStep.photo2Uri.isNotEmpty())
        {
            BadgedBox(badge ={
                IconButton(onClick = { onPhoto2Remove() }) {
                    Icon(
                        imageVector = Icons.Outlined.Remove,
                        contentDescription = stringResource(id = R.string.icon_descr),
                        tint = Color.Red)
                }
            } ) {
                AsyncImage(
                    model = procedureStep.photo2Uri,
                    contentDescription = stringResource(id = R.string.image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(imageSize))
            }

        }
        if (procedureStep.photo3Uri.isNotEmpty())
        {
            BadgedBox(badge ={
                IconButton(onClick = { onPhoto3Remove() }) {
                    Icon(
                        imageVector = Icons.Outlined.Remove,
                        contentDescription = stringResource(id = R.string.icon_descr),
                        tint = Color.Red)
                }
            } ) {
                AsyncImage(
                    model =procedureStep.photo3Uri ,
                    contentDescription = stringResource(id = R.string.image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(imageSize))
            }

        }
    }
}