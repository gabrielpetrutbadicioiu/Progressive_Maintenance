package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.composables

import android.net.Uri
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import coil.compose.AsyncImage
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.Divider
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.PmCardErrorState
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard

@Composable
fun InterventionSummarySection(
    problemDescription:String,
    problemDetailing:String,
    rootCause:String,
    observations:String,
    troubleshootSteps:String,
    pmCardErrorState: PmCardErrorState,
    measuresTaken:String,
    isInfoExpanded:Boolean,
    pmCard: ProgressiveMaintenanceCard,
    isNewIntervention:Boolean,
    isEditing:Boolean,

    onProblemDescriptionChange:(String)->Unit,
    onProblemDetailingChange:(String)->Unit,
    onRootCauseChange:(String)->Unit,
    onObservationsChange:(String)->Unit,
    onTroubleShootStepsChange:(String)->Unit,
    onMeasureTakenChange:(String)->Unit,
    onShowInfo:()->Unit,
    onDismissInfo:()->Unit,
    onPhoto1Remove:()->Unit,
    onPhoto2Remove:()->Unit,
    onPhoto3Remove:()->Unit,
    onUriResult:(Uri)->Unit,
    onCheckedChange:()->Unit,
    onImageClick:(String)->Unit

)
{
    val context= LocalContext.current
    val imageSize=64.dp
    val photoPickerLauncher= rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {uri->
        uri?.let { onUriResult(it) }


    }
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top)
    {
        Text(
            text = stringResource(id = R.string.intervention_summary),
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        //problem description
        if (isNewIntervention || isEditing)
        {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                isError = pmCardErrorState.isProblemDescrErr,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                value = problemDescription,
                onValueChange ={problemDescription-> onProblemDescriptionChange(problemDescription)},
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text(text = stringResource(id = R.string.problem_description))},
                supportingText = { Text(text = stringResource(id = R.string.problem_description_supporting_text))})
        }
        else{
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)
                    ),
                value = problemDescription,
                readOnly = true,
                onValueChange ={},
                shape = RoundedCornerShape(16.dp),
                supportingText = { Text(text = stringResource(id = R.string.problem_descr))})
        }


        Spacer(modifier = Modifier.height(16.dp))
        //problem detailing
        if (isEditing || isNewIntervention)
        {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                isError = pmCardErrorState.isProblemDetailErr,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                value = problemDetailing,
                onValueChange ={problemDetailing-> onProblemDetailingChange(problemDetailing)},
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text(text = stringResource(id = R.string.problem_detailing_placeholder))},
                supportingText = { Text(text = stringResource(id = R.string.problem_detailing_supporting_txt))})
        }
        else{
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)
                ),
                value = problemDetailing,
                onValueChange ={},
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                supportingText = { Text(text = stringResource(id = R.string.troubleshooting_steps))})
        }
        Divider(space = 16.dp, thickness = 2.dp, color = Color.DarkGray)

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically)
        { 
            Text(
        text = stringResource(id = R.string.post_intervention),
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(start = 16.dp)
                )
            Spacer(modifier = Modifier.width(2.dp))
            IconButton(onClick = {onShowInfo() }) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = stringResource(id = R.string.icon_descr),
                    tint = Color.White)
                DropdownMenu(expanded = isInfoExpanded,
                    onDismissRequest = { onDismissInfo() }) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.ida_info) )  },
                        onClick = {})
                    
                }
            }
}
        
        Spacer(modifier = Modifier.height(16.dp))

        //root cause
        if (isEditing || isNewIntervention)
        {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                isError = pmCardErrorState.isRootCauseErr,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                value = rootCause,
                onValueChange ={root-> onRootCauseChange(root)},
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text(text = stringResource(id = R.string.root_cause_ph))},
                supportingText = { Text(text = stringResource(id = R.string.root_cause_st))})
        }
        else{
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)
                ),
                value = rootCause.ifEmpty { stringResource(id = R.string.not_completed) },
                onValueChange ={},
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                supportingText = { Text(text = stringResource(id = R.string.root_cause))})
        }

        Spacer(modifier = Modifier.height(16.dp))

        //measures taken
        if (isEditing || isNewIntervention)
        {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                isError = pmCardErrorState.isCorrectiveMeasureErr,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                value =measuresTaken ,
                onValueChange ={measures-> onMeasureTakenChange(measures.replaceFirstChar { char-> char.uppercase() })},
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text(text = stringResource(id = R.string.measure_taken_ph))},
                supportingText = { Text(text = stringResource(id = R.string.measure_taken_st))})
        }
        else{
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)
                ),
                value =measuresTaken.ifEmpty { stringResource(id = R.string.not_completed) } ,
                onValueChange ={},
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                supportingText = { Text(text = stringResource(id = R.string.measures_taken))})
        }

        Spacer(modifier = Modifier.height(16.dp))

        //troubleshooting steps
        if (isEditing || isNewIntervention)
        {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                isError = pmCardErrorState.isRepairStepsErr,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                value = troubleshootSteps,
                onValueChange ={steps->
                    onTroubleShootStepsChange(steps)},
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text(text = stringResource(id = R.string.troubleshooting_steps_ph  ))},
                supportingText = { Text(text = stringResource(id = R.string.troubleshooting_steps_st))})
        }
        else{
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)
                    ),
                value = troubleshootSteps.ifEmpty { stringResource(id = R.string.not_completed) },
                onValueChange ={},
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                supportingText = { Text(text = stringResource(id = R.string.recommended_steps))})
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Observations
        if (isEditing || isNewIntervention)
        {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)),
                value = observations,
                onValueChange ={obs-> onObservationsChange(obs)},
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text(text = stringResource(id = R.string.observations_ph))},
                supportingText = { Text(text = stringResource(id = R.string.observations_st))}
            )
        }
        else{
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = colorResource(id = R.color.btn_color)
                ),
                value = observations.ifEmpty { stringResource(id = R.string.not_completed) },
                onValueChange ={},
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                supportingText = { Text(text = stringResource(id = R.string.observations_st))}
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (isEditing||isNewIntervention)
        {
            TextButton(onClick = {
                if (pmCard.photo1.isNotEmpty()&&pmCard.photo2.isNotEmpty()&&pmCard.photo3.isNotEmpty())
                {
                    Toast.makeText(context, "Maximum 3 images allowed!" , Toast.LENGTH_LONG).show()
                }
                else{
                    photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            }) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = R.string.add_3_photos))
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Outlined.AddAPhoto,
                        contentDescription = stringResource(id = R.string.icon_descr) )

                }
            }

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically)
            {

                if (pmCard.photo1.isNotEmpty())
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
                            contentScale = ContentScale.Crop,
                            model =pmCard.photo1 ,
                            contentDescription = stringResource(id = R.string.image_description),
                            modifier = Modifier.size(imageSize))
                    }
                }
                Spacer(modifier = Modifier.width(56.dp))

                if (pmCard.photo2.isNotEmpty())
                {
                    BadgedBox(badge = {
                        IconButton(onClick = { onPhoto2Remove() }) {
                            Icon(
                                imageVector = Icons.Outlined.Remove,
                                contentDescription = stringResource(id = R.string.icon_descr),
                                tint = Color.Red)
                        }
                    }) {
                        AsyncImage(
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(imageSize),
                            model =pmCard.photo2 ,
                            contentDescription = stringResource(id = R.string.image_description) )
                    }

                }
                Spacer(modifier = Modifier.width(56.dp))
                if (pmCard.photo3.isNotEmpty())
                {
                    BadgedBox(badge = {
                        IconButton(onClick = { onPhoto3Remove() }) {
                            Icon(
                                imageVector = Icons.Outlined.Remove,
                                contentDescription = stringResource(id = R.string.icon_descr),
                                tint = Color.Red)
                        }
                    }) {
                        AsyncImage(
                            modifier = Modifier.size(imageSize),
                            contentScale = ContentScale.Crop,
                            model = pmCard.photo3,
                            contentDescription = stringResource(id = R.string.image_description))
                    }
                }


            }

        }
        if (isEditing)
        {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = pmCard.resolved,
                    onCheckedChange = { onCheckedChange() })
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(id = R.string.mark_resolved))
            }
        }

        Row {
            if (pmCard.photo1.isNotEmpty())
            {
                AsyncImage(
                    modifier = Modifier.size(imageSize).clickable { onImageClick(pmCard.photo1) },
                    contentScale = ContentScale.Crop,
                    model = pmCard.photo1,
                    contentDescription = stringResource(id = R.string.image_description))
            }
            if (pmCard.photo2.isNotEmpty())
            {
                AsyncImage(
                    modifier = Modifier.size(imageSize).clickable { onImageClick(pmCard.photo2) },
                    contentScale = ContentScale.Crop,
                    model = pmCard.photo2,
                    contentDescription = stringResource(id = R.string.image_description))
            }
            if (pmCard.photo3.isNotEmpty())
            {
                AsyncImage(
                    modifier = Modifier.size(imageSize).clickable { onImageClick(pmCard.photo3) },
                    contentScale = ContentScale.Crop,
                    model = pmCard.photo3,
                    contentDescription = stringResource(id = R.string.image_description))
            }



        }
        
    }
}