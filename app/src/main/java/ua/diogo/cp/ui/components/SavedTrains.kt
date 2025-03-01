package ua.diogo.cp.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
            }
        }

        FooterButton(navController)
    }
}

@Composable
fun JorneyCard(
    jorney: Jorney,
    navController: NavController,
    onJorneyRemoved: (Jorney) -> Unit
) {
    val scope = rememberCoroutineScope()
    val itemVisibility = remember {
        Animatable(1f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                navController.navigate("trains/${jorney.trainNumber}")
            }
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text(
                text = "${jorney.serviceCode.designation} ${jorney.trainNumber}:\n" +
                        "${jorney.trainStops.first().station.designation} - ${jorney.trainStops.last().station.designation}"
            )
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .align(Alignment.CenterVertically)
                .background(MaterialTheme.colorScheme.errorContainer)
                .clickable {
                    scope.launch {
                        itemVisibility.animateTo(targetValue = 0f, animationSpec = tween(200))
                    }
                    onJorneyRemoved(jorney)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remover comboio",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
