package ua.diogo.cp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ua.diogo.cp.data.retrofit.TrainsInStationViewModel
import ua.diogo.cp.ui.components.Header
import ua.diogo.cp.ui.components.TrainCard

// https://www.cp.pt/sites/spring/station/trains?stationId=94-38000

@Composable
fun NextTrains(
    viewModel: TrainsInStationViewModel,
    stationId: String,
) {
    viewModel.fetchTrainsInStation(stationId)
    val trains = viewModel.trainsInStation.observeAsState(emptyList())
    val isLoading = viewModel.isLoading.observeAsState(false)

    if (isLoading.value) {
        Text("Loading...")
    } else {
        println("Trains: $trains")
        Column(
            modifier = Modifier
                .padding(top = 46.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Header("Próximos comboios")
                trains.value.forEach { train ->
                    TrainCard(train,false,true)
                }
                Spacer(modifier = Modifier.padding(bottom = 16.dp))
            }
        }
    }
}