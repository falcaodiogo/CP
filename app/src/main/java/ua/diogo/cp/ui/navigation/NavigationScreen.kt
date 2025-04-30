package ua.diogo.cp.ui.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.data.retrofit.JorneysViewModel
import ua.diogo.cp.data.retrofit.StationsViewModel
import ua.diogo.cp.data.retrofit.TrainsInStationViewModel
import ua.diogo.cp.gemini.viewmodel.ChatViewModel
import ua.diogo.cp.notifications.NotificationService
import ua.diogo.cp.ui.screens.ChatBotScreen
import ua.diogo.cp.ui.screens.HomeScreen
import ua.diogo.cp.ui.screens.NextTrains
import ua.diogo.cp.ui.screens.SettingsScreen
import ua.diogo.cp.ui.screens.TrainsScreen

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun NavigationScreens(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context,
    viewModel: StationsViewModel,
    viewModel2: TrainsInStationViewModel,
    viewModel3: JorneysViewModel,
    viewmodel4: ChatViewModel,
    notificationService: NotificationService
) {
    val userData = googleAuthUiClient.getSignedInUser()
    NavHost(navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) {
            googleAuthUiClient.getSignedInUser()
                ?.let { it1 -> HomeScreen(googleAuthUiClient, it1, viewModel, navController) }
        }
        composable(NavItem.Trains.path) {
            TrainsScreen(viewModel3, navController, googleAuthUiClient, notificationService)
        }
        composable(NavItem.Stations.path) {
            NextTrains(navController, viewModel2, viewModel, "0")
        }
        composable(NavItem.ChatBot.path) {
            if (userData != null) {
                ChatBotScreen(context, viewmodel4, userData)
            }
        }
        composable(NavItem.Settings.path) {
            SettingsScreen(userData, onSignOut, notificationService)
        }
        composable("trains/{trainId}") { backStackEntry ->
            val trainId = backStackEntry.arguments?.getString("trainId")
            if (trainId != null) {
                TrainsScreen(viewModel3, navController, googleAuthUiClient, notificationService)
            } else {
                TrainsScreen(viewModel3, navController, googleAuthUiClient, notificationService)
            }
        }
        composable("stations/{stationId}") { backStackEntry ->
            val stationId = backStackEntry.arguments?.getString("stationId")
            if (stationId != null) {
                NextTrains(navController, viewModel2, viewModel, stationId)
            }
        }
    }
}