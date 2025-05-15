package ro.gabrielbadicioiu.progressivemaintenance.core.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun Divider(space:Dp, thickness:Dp, color: Color)
{
    Spacer(modifier = Modifier.height(space))
    HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = color, thickness = thickness)
    Spacer(modifier = Modifier.height(space))
}