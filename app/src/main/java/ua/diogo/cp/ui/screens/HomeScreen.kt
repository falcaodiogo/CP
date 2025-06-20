package ua.diogo.cp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.authentication.UserData
import ua.diogo.cp.data.retrofit.StationsViewModel
import ua.diogo.cp.ui.components.ChatBotWidget
import ua.diogo.cp.ui.components.CpLogo
import ua.diogo.cp.ui.components.DropDownStations
import ua.diogo.cp.ui.components.SavedTrains
import ua.diogo.cp.ui.components.WebsiteWidget

@Composable
fun HomeScreen(
    googleAuthUiClient: GoogleAuthUiClient?,
    userData: UserData?,
    viewModel: StationsViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(top = 46.dp, start = 24.dp, end = 24.dp, bottom = 100.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (googleAuthUiClient != null) {
            CpLogo(googleAuthUiClient, userData)
        }

        DropDownStations(viewModel = viewModel, navController = navController)

        if (googleAuthUiClient != null) {
            SavedTrains(navController = navController, googleAuthUiClient)
        }

        ChatBotWidget(navController = navController)

        WebsiteWidget()

        Spacer(modifier = Modifier.padding(6.dp))
    }
}