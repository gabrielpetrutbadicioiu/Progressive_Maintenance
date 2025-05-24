package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.presentation.screen_createCL.composables

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
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
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineParameter

@Composable
fun CenterLineParameters(
    clParameter:CenterLineParameter,
    showDeleteBtn:Boolean,
    onParameterNameChange:(String, CenterLineParameter)->Unit,
    onParameterValueChange:(String, CenterLineParameter)->Unit,
    onDeleteClick:()->Unit,
    isParameterErr:Boolean
)
{

    Column {
            Divider(space = 4.dp, thickness = 2.dp, color = Color.LightGray)
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {
                OutlinedTextField(
                    value = clParameter.parameterName,
                    onValueChange = {parameterName-> onParameterNameChange(parameterName.replaceFirstChar { char-> char.uppercase() }, clParameter) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        focusedBorderColor = colorResource(id = R.color.btn_color)
                    ),
                    isError = isParameterErr,
                    supportingText = { Text(text = stringResource(id = R.string.parameter))},
                    modifier = Modifier.weight(0.5f),
                    placeholder = { Text(text = stringResource(id = R.string.parameter_name))}
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = clParameter.parameterValue,
                    onValueChange = {parameterValue->onParameterValueChange(parameterValue.replaceFirstChar { char->char.uppercase() }, clParameter)},
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        focusedBorderColor = colorResource(id = R.color.btn_color)
                    ),
                    isError = isParameterErr,
                    supportingText = { Text(text = stringResource(id = R.string.parameter_value))},
                    modifier = Modifier.weight(0.5f),
                    placeholder = { Text(text = stringResource(id = R.string.parameter_value_ph))}
                )
            }
        if (showDeleteBtn)
        {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start) {
                OutlinedButton(
                    onClick = { onDeleteClick()},
                    border = BorderStroke(1.dp, color = Color.Red),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Red,
                    )
                ) {
                    Text(text = stringResource(id = R.string.delete_cl_parameter) )
                }
            }
        }

            Divider(space = 4.dp, thickness = 2.dp, color = Color.LightGray)


        }
    }
