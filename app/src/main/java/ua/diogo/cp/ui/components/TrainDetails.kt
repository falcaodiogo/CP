package ua.diogo.cp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ua.diogo.cp.data.retrofit.entity.TrainsInStation

@Composable
fun TrainDetails(train: TrainsInStation, showImage: Boolean, rounded: Int) {
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