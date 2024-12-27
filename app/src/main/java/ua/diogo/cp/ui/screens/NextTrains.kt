package ua.diogo.cp.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

// https://www.cp.pt/sites/spring/station/trains?stationId=94-38000

//     {
//        "delay": 0,
//        "trainOrigin": {
//            "code": "94-34009",
//            "designation": "Entroncamento"
//        },
//        "trainDestination": {
//            "code": "94-2006",
//            "designation": "Porto Campanha"
//        },
//        "departureTime": "16:08",
//        "arrivalTime": "16:07",
//        "trainNumber": 821,
//        "trainService": {
//            "code": "IR",
//            "designation": "InterRegional"
//        },
//        "platform": "1",
//        "occupancy": null,
//        "eta": "16:07",
//        "etd": "16:08"
//    }

@Composable
fun NextTrains(
    viewModel: ViewModel,
    stationId: String,
) {
    Text(text = stationId)
}