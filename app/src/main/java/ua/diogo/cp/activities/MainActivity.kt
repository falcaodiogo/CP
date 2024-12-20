package ua.diogo.cp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ua.diogo.cp.ui.screens.MainScreen
import ua.diogo.cp.ui.screens.WelcomeScreen
import ua.diogo.cp.ui.theme.CPTheme
import ua.diogo.cp.ui.theme.backgroundLight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CPTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "welcome",
//                    enterTransition = {
//                                slideIntoContainer(
//                                    AnimatedContentTransitionScope.SlideDirection.Up,
//                                    tween(1000)
//                                ) + fadeIn()
//                    },
                ) {
                    composable("welcome") {
                        WelcomeScreen(modifier = Modifier, navController = navController)
                    }
                    composable("home") {
                        val navController2 = rememberNavController()
                        Surface(modifier = Modifier.fillMaxSize()) {
                            MainScreen(
                                navController = navController2,
                                context = applicationContext
                            )
                        }
                    }
                }
            }
        }
    }
}