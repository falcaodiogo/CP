package ua.diogo.cp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import ua.diogo.cp.data.retrofit.JorneysViewModel
import ua.diogo.cp.data.retrofit.entity.Jorney
import ua.diogo.cp.mathFunctHelpers.calculateTrainProgress
import ua.diogo.cp.mathFunctHelpers.currentStation
import ua.diogo.cp.mathFunctHelpers.nextStation
import ua.diogo.cp.ui.screens.getCurrentDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun NoResultsMessage() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text("Sem resultados.", fontSize = 16.sp, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ScreenTitle(title: String) {
    Text(title, fontSize = 24.sp)
}

@Composable
fun TrainSearchRow(trainCode: MutableState<String>, viewModel: JorneysViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.width(250.dp),
            value = trainCode.value,
            onValueChange = { trainCode.value = it },
            placeholder = { Text("Código do comboio") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {
            viewModel.fetchJorneys(trainCode.value, getCurrentDate())
        }) {
            Icon(Icons.Rounded.Search, contentDescription = "Comboio")
        }
    }
}

@Composable
fun LoadingIndicator() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text("", fontSize = 16.sp, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun JorneysList(jorneys: Jorney) {

    val currentTime = LocalTime.now()
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        JorneyHeader(jorneys)
        if (jorneys.delay != 0) {
            DelayInfoRow(delay = jorneys.delay, true)
        } else {
            DelayInfoRow(delay = jorneys.delay, true, MaterialTheme.colorScheme.primaryContainer)
        }
    }
    Spacer(modifier = Modifier.padding(12.dp))

    if (jorneys.status == "IN_TRANSIT") {
        InfoNextStation(text = "Próxima paragem: ${nextStation(jorneys)}")
    } else if (jorneys.status == "AT_STATION") {
        InfoNextStation(text = "Encontra-se parado em: ${currentStation(jorneys)}")
    } else if (jorneys.status == "COMPLETED") {
        InfoNextStation(text = "Comboio chegou ao destino às ${jorneys.trainStops[jorneys.trainStops.size - 1].arrival}")
    } else if (jorneys.status == "NEAR_NEXT") {
        InfoNextStation(text = "A dar entrada em: ${nextStation(jorneys, false)}")
    } else if (jorneys.status == null || jorneys.status == "NOT_STARTED" || jorneys.status == "AT_ORIGIN") {
        InfoNextStation(text = "Por partir.\nSairá às ${jorneys.trainStops[0].etd} na plataforma ${jorneys.trainStops[0].platform}.")
    }
    // estado suprimido?

    Spacer(modifier = Modifier.padding(12.dp))

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Text(
            text = "Paragens",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        Row {
            val progress = calculateTrainProgress(jorneys)
            println("Progress: $progress")
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(progress * 8.dp)
                    .padding(start = 12.dp, top = 26.dp)
                    .background(MaterialTheme.colorScheme.tertiary),
                contentAlignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
            Column {
                for (i in jorneys.trainStops.indices) {
                    val stationStop = jorneys.trainStops[i]
                    val etdTime = try {
                        LocalTime.parse(stationStop.etd, timeFormatter)
                    } catch (e: Exception) {
                        null
                    }

                    val hasPassed = etdTime != null && etdTime.isBefore(currentTime)

                    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = stationStop.station.designation,
                            color = if (hasPassed) Color.Gray else MaterialTheme.colorScheme.secondary,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                        )
                        val etd = jorneys.trainStops[i].etd
                        if (etd != null) {
                            Text(
                                text = jorneys.trainStops[i].etd,
                                color = if (hasPassed) Color.Gray else MaterialTheme.colorScheme.secondary,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoNextStation(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Informação em tempo real",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                )
                Pulsating(modifier = Modifier.padding(vertical = 16.dp)) {
                    Icon(
                        Icons.Rounded.MyLocation,
                        contentDescription = "Localização",
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            Text(
                text = text,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 18.dp),
            )
        }
    }
}

@Composable
fun JorneyHeader(jorneys: Jorney) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Column {
                Text(
                    text = "${jorneys.serviceCode.designation} ${jorneys.trainNumber}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp)
                )
                TrainInfo("Origem", jorneys.trainStops[0].station.designation, 100)
                TrainInfo(
                    "Destino",
                    jorneys.trainStops[jorneys.trainStops.size - 1].station.designation,
                    100
                )
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}