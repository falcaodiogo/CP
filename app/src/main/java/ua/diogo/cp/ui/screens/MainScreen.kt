package ua.diogo.cp.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.data.retrofit.JorneysViewModel
import ua.diogo.cp.data.retrofit.StationsViewModel
import ua.diogo.cp.data.retrofit.TrainsInStationViewModel
import ua.diogo.cp.gemini.viewmodel.ChatViewModel
import ua.diogo.cp.notifications.NotificationService
import ua.diogo.cp.ui.navigation.BottomNavigationBar
import ua.diogo.cp.ui.navigation.NavigationScreens

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context,
    viewModel: StationsViewModel,
    viewModel2: TrainsInStationViewModel,
    viewModel3: JorneysViewModel,
    viewmodel4: ChatViewModel
) {
    val postNotificationPermission =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val notificationService = NotificationService(context)
    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }
    Scaffold(bottomBar = {
        BottomAppBar(
            modifier = Modifier.height(100.dp)
        ) { BottomNavigationBar(navController = navController) }
    }) {
        NavigationScreens(
            navController = navController,
            onSignOut,
            googleAuthUiClient,
            context,
            viewModel,
            viewModel2,
            viewModel3,
            viewmodel4,
            notificationService
        )
    }
}