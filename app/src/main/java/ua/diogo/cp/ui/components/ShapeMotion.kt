package ua.diogo.cp.ui.components

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ua.diogo.cp.R
import ua.diogo.cp.ui.theme.tertiaryContainerLightMediumContrast

@Composable
fun ShapeMotion() {
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val offsetY = remember { Animatable(-80f) }
    val offsetY2 = remember { Animatable(330f) }
    val rotationAngle = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = EaseIn
                )
            )

            offsetY.animateTo(
                targetValue = -80f,
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = EaseInOutQuad
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        while (true) {

            offsetY2.animateTo(
                targetValue = 295f,
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = EaseInOutQuad
                )
            )

            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    1L,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )

            offsetY2.animateTo(
                targetValue = 330f,
                animationSpec = tween(
                    durationMillis = 1500,
                    easing = EaseInOutQuad
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            rotationAngle.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 20000,
                    easing = LinearEasing
                )
            )
            rotationAngle.snapTo(0f)
        }
    }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.aura),
                    contentDescription = "Animated Image",
                    colorFilter = ColorFilter.tint(tertiaryContainerLightMediumContrast),
                    modifier = Modifier
                        .wrapContentSize()
                        .offset(x = (-30).dp, y = offsetY.value.dp)
                        .graphicsLayer(rotationZ = rotationAngle.value)
                )
                Image(
                    painter = painterResource(id = R.drawable.pill),
                    contentDescription = "Animated Image",
                    colorFilter = ColorFilter.tint(tertiaryContainerLightMediumContrast),
                    modifier = Modifier
                        .wrapContentSize(unbounded = true, align = Alignment.TopStart)
                        .size(700.dp)
                        .offset(y = offsetY2.value.dp)
                )
            }
        }