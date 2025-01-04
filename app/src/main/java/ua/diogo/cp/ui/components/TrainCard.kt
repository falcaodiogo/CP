package ua.diogo.cp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ua.diogo.cp.R
import ua.diogo.cp.data.retrofit.entity.TrainsInStation
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TrainCard(
    train: TrainsInStation,
    showImage: Boolean,
    atrasoInfo: Boolean,
    navController: NavController
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable { isExpanded = !isExpanded }
            .animateContentSize(animationSpec = tween(durationMillis = 400))
    ) {
        TrainDetails(
            train = train,
            showImage = showImage,
            rounded = if (train.delay != 0 && atrasoInfo) 0 else 24
        )
        if (isExpanded) {
            ShimmeringArrivalTime(train, rememberShimmerBrush())
            ElevatedButton(
                modifier = Modifier
                    .padding(16.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                onClick = {
                    navController.navigate("trains/${train.trainNumber}")
                },
                contentPadding = PaddingValues(start = 16.dp)
            ) {
                Text(
                    text = "Seguir comboio",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    textAlign = TextAlign.Start
                )
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 8.dp))
        if (train.delay != 0 && atrasoInfo) {
            DelayInfoRow(delay = train.delay)
        }
    }
}

@Composable
private fun rememberShimmerBrush(): Brush {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f),
        MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.4f),
        MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
    )
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        initialValue = -20f, targetValue = 4000f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, delayMillis = 400),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    return remember(translateAnim) {
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnim, y = translateAnim)
        )
    }
}

@Composable
private fun TrainDetails(train: TrainsInStation, showImage: Boolean, rounded: Int) {
    Row(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                    bottomStart = rounded.dp,
                    bottomEnd = rounded.dp
                )
            )
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            TrainTitle(train)
            TrainInfo("Origem", train.trainOrigin.designation, 15)
            TrainInfo("Destino", train.trainDestination.designation, 15)
            Spacer(modifier = Modifier.padding(4.dp))
        }
        if (showImage) {
            TrainImage()
        }
    }
}

@Composable
fun TrainTitle(train: TrainsInStation) {
    Text(
        text = "${train.trainService.designation} ${train.trainNumber}",
        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
private fun ShimmeringArrivalTime(train: TrainsInStation, brush: Brush) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val arrivalTime = runCatching { LocalTime.parse(train.arrivalTime, formatter) }.getOrNull()
    val updatedTime = arrivalTime?.plusMinutes((train.delay ?: 0).toLong())
    val displayTime = updatedTime?.format(formatter) ?: train.arrivalTime

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(brush),
    ) {
        if (displayTime == null) {
            Text(
                text = "Linha ${train.platform ?: "Indefinida"}",
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(16.dp)
            )
        } else if (displayTime == null && train.platform == null) {
            Text(
                text = "Por favor, consulte o painel de informação",
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Text(
                text = "Linha ${train.platform ?: "Indefinida"} às ${displayTime ?: "Indefinida"}",
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun TrainInfo(label: String, value: String, maxChars: Int) {
    Text(
        text = "$label: ${if (value.length > maxChars) "${value.take(maxChars)}..." else value}",
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    )
}

@Composable
private fun TrainImage() {
    Image(
        painter = painterResource(id = R.drawable.cplogo),
        contentDescription = "CP logo",
        modifier = Modifier
            .height(34.dp)
            .padding(horizontal = 24.dp)
    )
}

@Composable
fun DelayInfoRow(
    delay: Int,
    onlyDelay: Boolean = false,
    color: Color = MaterialTheme.colorScheme.errorContainer
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(color),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val hours = delay / 60
        val minutes = delay % 60

        val delayText = when {
            delay <= 0 -> "Sem atraso"
            hours > 0 -> {
                val hoursText = if (hours == 1) "hora" else "horas"
                val minutesText = if (minutes == 0) "" else " e $minutes ${if (minutes == 1) "minuto" else "minutos"}"
                "atraso de $hours $hoursText$minutesText"
            }
            else -> "atraso de $delay ${if (delay == 1) "minuto" else "minutos"}"
        }

        if (!onlyDelay || delay > 0) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                text = if (!onlyDelay) "Circula com $delayText" else delayText
            )
        } else {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                text = "Sem atraso"
            )
        }
    }
}

