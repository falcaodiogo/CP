package ua.diogo.cp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import ua.diogo.cp.ui.components.HomeScreen
import ua.diogo.cp.ui.theme.CPTheme
import ua.diogo.cp.ui.theme.backgroundLight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CPTheme {
                HomeScreen(modifier = Modifier.background(color = backgroundLight))
            }
        }
    }
}