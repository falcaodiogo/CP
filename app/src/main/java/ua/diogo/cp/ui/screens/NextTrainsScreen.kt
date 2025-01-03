package ua.diogo.cp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material.icons.rounded.Train
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ua.diogo.cp.R
import ua.diogo.cp.data.retrofit.TrainsInStationViewModel
import ua.diogo.cp.ui.components.FooterButton
import ua.diogo.cp.ui.components.Header
import ua.diogo.cp.ui.components.TrainCard

// https://www.cp.pt/sites/spring/station/trains?stationId=94-38000

@Composable
fun NextTrains(
    navController: NavController,
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
                .padding(top = 46.dp, start = 16.dp, end = 16.dp, bottom = 136.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .height(IntrinsicSize.Max),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxWidth()
                    .height(240.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cplogo),
                    contentDescription = "Train icon",
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = "Próximos comboios",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Header("Próximos comboios")
                if (trains.value.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 36.dp)
                            .padding(24.dp)
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Rounded.QuestionMark,
                                contentDescription = "Cancel Presentation",
                                modifier = Modifier.size(36.dp)
                            )
                            Icon(
                                imageVector = Icons.Rounded.Train,
                                contentDescription = "Cancel Presentation",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("Hoje não existem mais comboios", fontSize = 32.sp, lineHeight = 40.sp)
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            FooterButton(text = "Voltar",
                                icon = Icons.Rounded.ArrowBackIosNew,
                                onClick = { navController.navigate("home") })
                        }
                    }


                } else {
                    trains.value.forEach { train ->
                        TrainCard(train, false, true)
                    }
                }
                Spacer(modifier = Modifier.padding(bottom = 16.dp))
            }
        }
    }
}