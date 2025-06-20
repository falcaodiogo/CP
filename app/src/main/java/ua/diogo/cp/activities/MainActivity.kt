package ua.diogo.cp.activities

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.authentication.SignInViewModel
import ua.diogo.cp.data.database.initialization.CPDatabase
import ua.diogo.cp.data.retrofit.JorneysViewModel
import ua.diogo.cp.data.retrofit.StationsViewModel
import ua.diogo.cp.data.retrofit.TrainsInStationViewModel
import ua.diogo.cp.gemini.viewmodel.ChatViewModel
import ua.diogo.cp.ui.screens.MainScreen
import ua.diogo.cp.ui.screens.WelcomeScreen
import ua.diogo.cp.ui.theme.CPTheme

class MainActivity : ComponentActivity() {

    private val viewModel: StationsViewModel by viewModels()
    private val viewModel2: TrainsInStationViewModel by viewModels()
    private val viewModel3: JorneysViewModel by viewModels()
    private val viewModel4: ChatViewModel by viewModels()

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        if (granted) {
            Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    fun requestLocationPermission() {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CPDatabase::class.java,
            "cp_database.db"
        ).build()
    }

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext),
            userDao = db.userDao
        )
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars =
            false
        enableEdgeToEdge(
            statusBarStyle =
            SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        super.onCreate(savedInstanceState)

        //notifications
        val notificationChannel = NotificationChannel(
            "notification_channel_id",
            "Seguimento do comboio CP",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

        setContent {
            CPTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "welcome") {
                        composable("welcome") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate("home")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Bem-vindo à nova app da CP",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate("home")
                                    viewModel.resetState()
                                }
                            }

                            WelcomeScreen(modifier = Modifier,
                                navController = navController,
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                })

                        }
                        composable("home") {
                            val navController2 = rememberNavController()
                            Surface(modifier = Modifier.fillMaxSize()) {
                                MainScreen(
                                    navController = navController2,
                                    googleAuthUiClient = googleAuthUiClient,
                                    onSignOut = {
                                        lifecycleScope.launch {
                                            googleAuthUiClient.signOut()
                                            Toast.makeText(
                                                applicationContext,
                                                "Signed out",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            navController.popBackStack()
                                        }
                                    },
                                    context = applicationContext,
                                    viewModel = viewModel,
                                    viewModel2 = viewModel2,
                                    viewModel3 = viewModel3,
                                    viewmodel4 = viewModel4
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}