package ro.gabrielbadicioiu.progressivemaintenance.core.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun DisplayImageScreen(imageUri:String)
{
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        AsyncImage(
            model = imageUri,
            contentDescription = stringResource(id = R.string.image_description))
        
    }
}