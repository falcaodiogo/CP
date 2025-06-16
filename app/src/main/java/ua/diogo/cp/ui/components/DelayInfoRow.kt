package ua.diogo.cp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
            delay <= 0 -> "Sem atraso atraso"
            hours > 0 -> {
                val hoursText = if (hours == 1) "hora" else "horas"
                val minutesText =
                    if (minutes == 0) "" else " e $minutes ${if (minutes == 1) "minuto" else "minutos"}"
                "atraso de $hours $hoursText$minutesText"
            }

            else -> "atraso de $delay ${if (delay == 1) "minuto" else "minutos"}"
        }

        if (!onlyDelay || delay > 0) {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                text = if (!onlyDelay) "Circula com $delayText" else "Circula com $delayText"
            )
        } else {
            Text(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                text = "Sem atraso"
            )
        }
    }
}