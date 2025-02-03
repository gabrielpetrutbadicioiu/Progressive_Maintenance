package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_registerCompanyEmail


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterCompanyEmailScreen()
{
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_icon),
                                contentDescription = stringResource(id = R.string.image_descr),
                                modifier = Modifier.size(86.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.bar_color),
                        scrolledContainerColor = colorResource(id = R.color.scroll_bar_color)
                    )
                )
            },
        )
        { innerPadding ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){

                Image(
                    painter = painterResource(id = R.drawable.auth_image),
                    contentDescription = stringResource(
                        id = R.string.image_description
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange ={/*TODO*/},
                    leadingIcon = { Icon(
                        imageVector =Icons.Default.Email,
                        contentDescription = stringResource(id = R.string.icon_descr) )},
                    isError = false,//TODO
                    supportingText = { Text(text = stringResource(id = R.string.required))},
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text(text = stringResource(id = R.string.enter_business_email))}
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange ={/*TODO*/},
                    trailingIcon = { IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector =Icons.Default.Visibility,//TODO
                            contentDescription = stringResource(id = R.string.icon_descr))
                    }
                       },
                    isError = false,//TODO
                    supportingText = { Text(text = stringResource(id = R.string.required))},
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text(text = stringResource(id = R.string.password_hint))},
                    singleLine = true,
                    visualTransformation = VisualTransformation.None//TODO
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange ={/*TODO*/},
                    trailingIcon = { IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector =Icons.Default.Visibility,//TODO
                            contentDescription = stringResource(id = R.string.icon_descr))
                    }
                    },
                    isError = false,//TODO
                    supportingText = { Text(text = stringResource(id = R.string.required))},
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text(text = stringResource(id = R.string.confirm_pass_label))},
                    singleLine = true,
                    visualTransformation = VisualTransformation.None//TODO
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { /*TODo*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    enabled = true,//TODO
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.bar_color))
                ) {
                    Text(text = stringResource(id = R.string.continue_btn))
                }
            }
        }
}}