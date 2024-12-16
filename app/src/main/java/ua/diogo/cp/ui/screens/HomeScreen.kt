package ua.diogo.cp.ui.screens

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Surface {
        Text(text = "HomeScreen", modifier = modifier)
    }
}