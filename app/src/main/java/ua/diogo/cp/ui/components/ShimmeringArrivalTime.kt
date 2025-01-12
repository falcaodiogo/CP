package ua.diogo.cp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ua.diogo.cp.data.retrofit.entity.TrainsInStation
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ShimmeringArrivalTime(train: TrainsInStation, brush: Brush) {
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
