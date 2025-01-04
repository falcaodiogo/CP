package ua.diogo.cp.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.data.database.dao.UserDao
import ua.diogo.cp.data.retrofit.JorneysViewModel
import ua.diogo.cp.data.retrofit.StationsViewModel
import ua.diogo.cp.data.retrofit.TrainsInStationViewModel
import ua.diogo.cp.gemini.viewmodel.ChatViewModel
import ua.diogo.cp.ui.navigation.BottomNavigationBar
import ua.diogo.cp.ui.navigation.NavigationScreens

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context,
    userDao: UserDao,
    viewModel: StationsViewModel,
    viewModel2: TrainsInStationViewModel,
    viewModel3: JorneysViewModel,
    viewmodel4: ChatViewModel
) {
    Scaffold(bottomBar = {
        BottomAppBar(
            modifier = Modifier.height(120.dp)
        ) { BottomNavigationBar(navController = navController) }
    }) { NavigationScreens(navController = navController, onSignOut, googleAuthUiClient, context, userDao, viewModel, viewModel2, viewModel3, viewmodel4) }
}