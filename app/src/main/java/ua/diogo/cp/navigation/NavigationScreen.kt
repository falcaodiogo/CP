package ua.diogo.cp.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun NavigationScreens(
    navController: NavHostController,
    onSignOut: () -> Unit,
//    googleAuthUiClient: GoogleAuthUiClient,
//    context: Context,
//    exerciseDao: ExerciseDao,
//    userDao: UserDao
) {
//    val viewModel = DailyExerciseViewModel(
//        exerciseDao = exerciseDao,
//        googleAuthUiClient = googleAuthUiClient,
//        userDao = userDao
//    )

//    NavHost(navController, startDestination = NavItem.Home.path) {
//        composable(NavItem.Home.path) { googleAuthUiClient.getSignedInUser()
//            ?.let { it1 -> HomeScreen(googleAuthUiClient, it1) } }
//        composable(NavItem.Notifications.path) { NotificationsScreen(navController, context) }
//        composable(NavItem.Sleep.path) { SleepScreen(context) }
//        composable(NavItem.Settings.path) {
//            val userData = googleAuthUiClient.getSignedInUser()
//            ProfileScreen(userData, onSignOut, context, googleAuthUiClient)
//        }
//        composable(NavItem.Exercises.path) { PlannedExercises(navController) }
//        composable(NavItem.Exercises.path + "/{day}") {backStackEntry ->
//            val day = backStackEntry.arguments?.getString("day")
//            DailyExercisesScreen(day, viewModel = viewModel, context)
//        }
//    }
}