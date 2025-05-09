package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.composables

import android.net.Uri
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

)
{
    val context= LocalContext.current
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

        Spacer(modifier = Modifier.height(16.dp))
        //problem detailing
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

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = Color.DarkGray,
            thickness = 2.dp)
        Spacer(modifier = Modifier.height(16.dp))
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
        TextField(
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(id = R.color.btn_color)),
            value = rootCause,
            onValueChange ={root-> onRootCauseChange(root)},
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(text = stringResource(id = R.string.root_cause_ph))},
            supportingText = { Text(text = stringResource(id = R.string.root_cause_st))})

        Spacer(modifier = Modifier.height(16.dp))

        //measures taken
        TextField(
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(id = R.color.btn_color)),
            value =measuresTaken ,
            onValueChange ={measures-> onMeasureTakenChange(measures.replaceFirstChar { char-> char.uppercase() })},
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(text = stringResource(id = R.string.measure_taken_ph))},
            supportingText = { Text(text = stringResource(id = R.string.measure_taken_st))})

        Spacer(modifier = Modifier.height(16.dp))

        //troubleshooting steps
        TextField(
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(id = R.color.btn_color)),
            value = troubleshootSteps,
            onValueChange ={steps->
                onTroubleShootStepsChange(steps)},
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(text = stringResource(id = R.string.troubleshooting_steps_ph  ))},
            supportingText = { Text(text = stringResource(id = R.string.troubleshooting_steps_st))})

        Spacer(modifier = Modifier.height(16.dp))




        //Observations
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
        Spacer(modifier = Modifier.height(16.dp))

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
            verticalAlignment = Alignment.CenterVertically) {
            val imageSize=64.dp
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
}