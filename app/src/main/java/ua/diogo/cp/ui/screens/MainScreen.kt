package ua.diogo.cp.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ua.diogo.cp.navigation.BottomNavigationBar
import ua.diogo.cp.navigation.NavigationScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    context: Context
) {
    Scaffold(bottomBar = {
        BottomAppBar(
            modifier = Modifier.height(120.dp)
        ) { BottomNavigationBar(navController = navController) }
    }) { NavigationScreens(navController = navController, context) }
}