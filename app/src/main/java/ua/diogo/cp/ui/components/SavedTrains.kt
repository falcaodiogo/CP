package ua.diogo.cp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.data.retrofit.entity.Jorney

@Composable
fun SavedTrains(
    navController: NavController,
    googleAuthUiClient: GoogleAuthUiClient
) {
    var savedJorneys by remember { mutableStateOf<List<Jorney>>(emptyList()) }

    LaunchedEffect(Unit) {
        val journeys = googleAuthUiClient.getSavedJorneys()
        savedJorneys = journeys
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(32.dp))
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Header("Comboios guardados")

        if (savedJorneys.isEmpty()) {
            Text(
                text = "Adicione comboios aos favoritos!",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                savedJorneys.forEach { jorney ->
                    JorneyCard(jorney, navController)
                }
            }
        }

        FooterButton(navController)
    }
}

@Composable
fun JorneyCard(jorney: Jorney, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                navController.navigate("trains/${jorney.trainNumber}")
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("${jorney.serviceCode.designation} ${jorney.trainNumber}:\n${jorney.trainStops[0].station.designation} - ${jorney.trainStops[jorney.trainStops.size - 1].station.designation}")
        }
    }
}
