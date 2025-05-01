package ua.diogo.cp.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.data.retrofit.entity.Jorney
import ua.diogo.cp.mathFunctHelpers.calculateTrainProgress
import ua.diogo.cp.mathFunctHelpers.currentStation
import ua.diogo.cp.mathFunctHelpers.nextStation
import ua.diogo.cp.notifications.NotificationService
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun JorneysList(
    jorneys: Jorney,
    googleAuthUiClient: GoogleAuthUiClient,
    notificationService: NotificationService
) {

    val currentTime = LocalTime.now()
    val progress = calculateTrainProgress(jorneys)
    val trainName =
        (jorneys.serviceCode.designation + " ${jorneys.trainNumber}") ?: "Desconhecido"
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    var isSaved by remember { mutableStateOf(true) }

    LaunchedEffect(jorneys) {
        isSaved = googleAuthUiClient.isJorneySaved(jorneys)
        if (isSaved) {
            notificationService.updateProgressNotification(jorneys)
        }
//        println("Is saved: $isSaved")
    }

    LaunchedEffect(jorneys) {
        val seenStations = mutableSetOf<String>()
        if (jorneys.status != "COMPLETED") {
            if (jorneys.status == "AT_STATION") {
                val currentStation = currentStation(jorneys)
                if (currentStation != null && !seenStations.contains(currentStation)) {
                    notificationService.showProgressNotification(trainName, progress, currentStation)
                    seenStations.add(currentStation)
                }
            }
        } else {
            notificationService.completeProgressNotification()
        }
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        JorneyHeader(jorneys)
        if (jorneys.delay == null && jorneys.status == null) {
            DelayInfoRow(delay = jorneys.delay, true, true, Color.Yellow)
        }
        if (jorneys.delay != 0 || jorneys.delay < 0) {
            DelayInfoRow(delay = jorneys.delay, true, false)
        } else {
            DelayInfoRow(
                delay = jorneys.delay,
                true,
                false,
                MaterialTheme.colorScheme.primaryContainer
            )
        }
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .height(100.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    if (isSaved) {
                        CoroutineScope(Dispatchers.Main).launch {
                            googleAuthUiClient.removeJorneyFromUser(jorneys)
                            isSaved = false
                            Toast.makeText(
                                googleAuthUiClient.context,
                                "Comboio removido dos favoritos.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            googleAuthUiClient.addJorneyToUser(jorneys)
                            isSaved = true
                            Toast.makeText(
                                googleAuthUiClient.context,
                                "Comboio adicionado aos favoritos.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }) {
                if (isSaved) {
                    Icon(Icons.Default.Favorite, contentDescription = "Remover")
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Remover", textAlign = TextAlign.Center)
                } else {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Guardar")
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Guardar", textAlign = TextAlign.Center)
                }
            }

            Button(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
                enabled = false,
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    if (isSaved) {
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                googleAuthUiClient.context,
                                "Notificações desativadas.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                googleAuthUiClient.context,
                                "Notificaçãos ativadas.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }) {
                if (isSaved) {
                    Icon(Icons.Default.Notifications, contentDescription = "Remover")
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Notificação\nativa", textAlign = TextAlign.Center)
                } else {
                    Icon(Icons.Default.NotificationsNone, contentDescription = "Guardar")
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        "Notificação\n" +
                                "não ativa", textAlign = TextAlign.Center
                    )
                }
            }
        }
    }


    if (jorneys.status == "IN_TRANSIT") {
        InfoNextStation(text = "Próxima paragem: ${nextStation(jorneys)}")
    } else if (jorneys.status == "AT_STATION") {
        InfoNextStation(text = "Encontra-se parado em: ${currentStation(jorneys)}")
    } else if (jorneys.status == "COMPLETED") {
        InfoNextStation(text = "Comboio chegou ao destino às ${jorneys.trainStops[jorneys.trainStops.size - 1].arrival}")
    } else if (jorneys.status == "NEAR_NEXT") {
        InfoNextStation(text = "A dar entrada em: ${currentStation(jorneys)}")
    } else if (jorneys.status == "NOT_STARTED" || jorneys.status == "AT_ORIGIN") {
        InfoNextStation(text = "Por partir.\nSairá às ${jorneys.trainStops[0].etd} na plataforma ${jorneys.trainStops[0].platform}.")
    } else if (jorneys.status == null) {
        InfoNextStation(text = "Ainda sem informações sobre o estado do comboio.")
    }
    // estado suprimido?


    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Text(
            text = "Paragens",
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        )
        Row {
            println("Progress: $progress")
            Box(
                modifier = Modifier
                    .width(24.dp)
                    .fillMaxHeight()
                    .padding(start = 8.dp, top = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(progress * 5.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stationStop.station.designation,
                            color = if (hasPassed) Color.Gray else MaterialTheme.colorScheme.secondary,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 24.dp),
                            maxLines = 1
                        )
                        val etd = jorneys.trainStops[i].etd
                        val eta = jorneys.trainStops[i].eta
                        if (etd != null) {
                            Text(
                                text = jorneys.trainStops[i].etd,
                                color = if (hasPassed) Color.Gray else MaterialTheme.colorScheme.secondary,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(bottom = 16.dp),
                                maxLines = 1
                            )
                        } else if (eta != null) {
                            Text(
                                text = jorneys.trainStops[i].eta,
                                color = if (hasPassed) Color.Gray else MaterialTheme.colorScheme.secondary,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .padding(bottom = 16.dp),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}