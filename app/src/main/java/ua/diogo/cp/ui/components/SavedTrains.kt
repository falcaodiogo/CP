package ua.diogo.cp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.data.retrofit.entity.Jorney

@Composable
fun SavedTrains(
    navController: NavController,
    googleAuthUiClient: GoogleAuthUiClient
) {
    var savedJorneys by remember { mutableStateOf<List<Jorney>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        savedJorneys = googleAuthUiClient.getSavedJorneys()
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
                fontWeight = FontWeight.Medium,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                savedJorneys.asReversed().forEach { jorney ->
                    JorneyCard(
                        jorney,
                        navController,
                        onJorneyRemoved = { removedJorney ->
                            coroutineScope.launch {
                                googleAuthUiClient.removeJorneyFromUser(removedJorney)
                                savedJorneys =
                                    googleAuthUiClient.getSavedJorneys()
                            }
                        })
                }
                Text(
                    "Deslize para ações",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        FooterButton(navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun JorneyCard(
    jorney: Jorney,
    navController: NavController,
    onJorneyRemoved: (Jorney) -> Unit
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd) {
                navController.navigate("trains/${jorney.trainNumber}")
            } else if (it == DismissValue.DismissedToStart) {
                onJorneyRemoved(jorney)
            }
            true
        }
    )

    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier.padding(vertical = 4.dp),
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(0.5f)
        },
        background = {
            val direction = dismissState.dismissDirection
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        when (direction) {
                            DismissDirection.StartToEnd -> MaterialTheme.colorScheme.primary
                            DismissDirection.EndToStart -> MaterialTheme.colorScheme.error
                            null -> Color.Transparent
                        }
                    )
            ) {
                when (direction) {
                    DismissDirection.StartToEnd -> {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Train,
                                contentDescription = "View train details",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                "Ver mais",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    DismissDirection.EndToStart -> {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "Apagar",
                                color = MaterialTheme.colorScheme.onError,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.onError,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    null -> Unit
                }
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surface)
                .clickable {
                    navController.navigate("trains/${jorney.trainNumber}")
                }
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .align(Alignment.CenterVertically)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Train,
                    contentDescription = "Remover comboio",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "${jorney.serviceCode.designation} ${jorney.trainNumber}:\n" +
                            "${jorney.trainStops.first().station.designation} - ${jorney.trainStops.last().station.designation}"
                )
            }
        }
    }
}