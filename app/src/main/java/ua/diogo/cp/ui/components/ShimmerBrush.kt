package ua.diogo.cp.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

@Composable
fun rememberShimmerBrush(): Brush {
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