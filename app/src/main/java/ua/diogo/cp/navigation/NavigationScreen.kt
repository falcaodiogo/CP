package ua.diogo.cp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.diogo.cp.ui.screens.ChatBotScreen
import ua.diogo.cp.ui.screens.HomeScreen
import ua.diogo.cp.ui.screens.SettingsScreen
import ua.diogo.cp.ui.screens.StallmentsScreen
import ua.diogo.cp.ui.screens.TrainsScreen

@Composable
fun NavigationScreens(
    navController: NavHostController,
    context: Context
) {
    NavHost(navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) {
            HomeScreen()
        }
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
            SettingsScreen(context)
        }
    }
}