package ua.diogo.cp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ua.diogo.cp.R
import ua.diogo.cp.data.retrofit.entity.TrainsInStation

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
            DelayInfoRow(delay = train.delay, supressed = false)
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
fun TrainInfo(label: String, value: String, maxChars: Int) {
    Text(
        text = "$label: ${if (value.length > maxChars) "${value.take(maxChars)}..." else value}",
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    )
}

@Composable
fun TrainImage() {
    Image(
        painter = painterResource(id = R.drawable.cplogo),
        contentDescription = "CP logo",
        modifier = Modifier
            .height(34.dp)
            .padding(horizontal = 24.dp)
    )
}