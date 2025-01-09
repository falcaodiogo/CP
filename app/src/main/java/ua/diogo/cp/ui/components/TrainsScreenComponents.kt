package ua.diogo.cp.ui.components

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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.data.database.dao.UserDao
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
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(160.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun TrainSearchRow(trainCode: MutableState<String>, viewModel: JorneysViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .align(Alignment.CenterVertically),
            value = trainCode.value,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = { trainCode.value = it },
            placeholder = { Text("Código do comboio") })
    }

    LaunchedEffect(trainCode.value) {
        while (trainCode.value.isNotEmpty()) {
            viewModel.fetchJorneys(trainCode.value, getCurrentDate())
            delay(30000L)
        }
    }
}


@Composable
fun LoadingIndicator() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            "A atualizar os dados. Por favor aguarde.",
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun JorneysList(jorneys: Jorney, userDao: UserDao, googleAuthUiClient: GoogleAuthUiClient) {

    val currentTime = LocalTime.now()
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        JorneyHeader(jorneys)
        if (jorneys.delay != 0) {
            DelayInfoRow(delay = jorneys.delay, true)
        } else {
            DelayInfoRow(delay = jorneys.delay, true, MaterialTheme.colorScheme.primaryContainer)
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
//                    googleAuthUiClient.getSignedInUser()
//                    ?.let { addTrainToUser(userDao, it.userId, jorneys)
                }) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Guardar")
                Spacer(modifier = Modifier.padding(8.dp))
                Text("Guardar", textAlign = TextAlign.Center)
            }

            Button(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
                enabled = false,
                shape = RoundedCornerShape(16.dp),
                onClick = { /* TODO */ }) {
                Icon(Icons.Default.Notifications, contentDescription = "Notificações")
                Spacer(modifier = Modifier.padding(8.dp))
                Text("Ativar\nnotificações", textAlign = TextAlign.Center)
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
    } else if (jorneys.status == null || jorneys.status == "NOT_STARTED" || jorneys.status == "AT_ORIGIN") {
        InfoNextStation(text = "Por partir.\nSairá às ${jorneys.trainStops[0].etd} na plataforma ${jorneys.trainStops[0].platform}.")
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
            val progress = calculateTrainProgress(jorneys)
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
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Informação em tempo real",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(24.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Pulsating(modifier = Modifier.padding(8.dp)) {
                    Icon(
                        Icons.Rounded.MyLocation, contentDescription = "Localização"
                    )
                }
                Text(
                    text = text,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    lineHeight = 28.sp,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}

@Composable
fun JorneyHeader(jorneys: Jorney) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Column {
                Text(
                    text = "${jorneys.serviceCode.designation} ${jorneys.trainNumber}",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 8.dp)
                )
                TrainInfo("Origem", jorneys.trainStops[0].station.designation, 100)
                TrainInfo(
                    "Destino",
                    jorneys.trainStops[jorneys.trainStops.size - 1].station.designation,
                    100
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}

//fun addTrainToUser(userDao: UserDao, userId: String, jorney: Jorney) {
//    CoroutineScope(Dispatchers.IO).launch {
//        userDao.addTrainToUser(userId, jorney)
//        println("Train added to user")
//    }
//}
