package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.PmCardErrorState

@Composable
fun InterventionSummarySection(
    problemDescription:String,
    problemDetailing:String,
    rootCause:String,
    observations:String,
    troubleshootSteps:String,
    pmCardErrorState: PmCardErrorState,

    onProblemDescriptionChange:(String)->Unit,
    onProblemDetailingChange:(String)->Unit,
    onRootCauseChange:(String)->Unit,
    onObservationsChange:(String)->Unit,
    onTroubleShootStepsChange:(String)->Unit

)
{
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

        //Observations
        TextField(
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(id = R.color.btn_color)),
            value = observations,
            onValueChange ={obs-> onObservationsChange(obs)},
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(text = stringResource(id = R.string.observations_ph))},
            supportingText = { Text(text = stringResource(id = R.string.observations_st))})

    }
}