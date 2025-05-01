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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ua.diogo.cp.data.retrofit.entity.TrainsInStation
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ShimmeringArrivalTime(
    train: TrainsInStation,
    brush: Brush,
    currentTime: LocalTime = LocalTime.now()
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    val scheduledArrival = runCatching {
        LocalTime.parse(train.arrivalTime, formatter)
    }.getOrNull()

    val actualArrival = scheduledArrival?.plusMinutes((train.delay ?: 0).toLong())
    val displayTime = actualArrival?.format(formatter) ?: train.arrivalTime

    val status = when {
        train.arrivalTime == null && train.departureTime == null && scheduledArrival?.isBefore(
            currentTime
        ) == true -> "SUPPRESSED"

        train.delay == null -> "ON_TIME"
        train.delay!! > 0 -> "DELAYED"
        else -> "ON_TIME"
    }

    println(train)

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(brush)
    ) {
        when {
            status == "SUPPRESSED" -> {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Yellow)) {
                            append("Comboio suprimido")
                        }
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(16.dp)
                )
            }

            train.platform == null -> {
                Text(
                    text = buildAnnotatedString {
                        append("Chegada prevista: ")
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(displayTime)
                        }
                        append("\nConsulte o painel de informação")
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(16.dp)
                )
            }

            status == "DELAYED" -> {
                Text(
                    text = buildAnnotatedString {
                        append("Linha ${train.platform} ")
                        append("às ")
                        append(displayTime)
                    },
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                if (displayTime == null) {
                    Text(
                        text = "Linha ${train.platform}",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    Text(
                        text = "Linha ${train.platform} às $displayTime",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}