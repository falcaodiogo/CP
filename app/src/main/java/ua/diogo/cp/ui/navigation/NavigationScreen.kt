package ua.diogo.cp.ui.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.database.dao.UserDao
import ua.diogo.cp.ui.screens.ChatBotScreen
import ua.diogo.cp.ui.screens.HomeScreen
import ua.diogo.cp.ui.screens.SettingsScreen
import ua.diogo.cp.ui.screens.StallmentsScreen
import ua.diogo.cp.ui.screens.TrainsScreen

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun NavigationScreens(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context,
    userDao: UserDao
) {
    NavHost(navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) { googleAuthUiClient.getSignedInUser()
            ?.let { it1 -> HomeScreen(googleAuthUiClient, it1) } }
        composable(NavItem.Trains.path) {
            TrainsScreen(context)
        }
        composable(NavItem.Stallments.path) {
            StallmentsScreen(context)
        }
        composable(NavItem.ChatBot.path) {
            ChatBotScreen(context)
        }
        composable(NavItem.Settings.path) {
            val userData = googleAuthUiClient.getSignedInUser()
            SettingsScreen(userData, onSignOut, context, googleAuthUiClient)
        }
    }
}