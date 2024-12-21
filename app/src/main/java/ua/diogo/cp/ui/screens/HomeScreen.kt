package ua.diogo.cp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.authentication.UserData
import ua.diogo.cp.data.retrofit.StationsViewModel

@Composable
fun HomeScreen(
    googleAuthUiClient: GoogleAuthUiClient,
    userData: UserData,
    viewModel: StationsViewModel
) {
    val stations by viewModel.stations.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.fetchStations()
        println("Fetching stations")
        println("${viewModel.stations.value}")
    }
    LaunchedEffect(Unit) {
        viewModel.fetchStations()
        println("Fetching stations")
    }

    Column {
        if (isLoading) {
            Text(text = "Loading...")
        } else if (stations.isEmpty()) {
            Text(text = "No stations available.")
        } else {
            stations.forEach { station ->
                Text(text = station.code)
            }
        }
    }
}