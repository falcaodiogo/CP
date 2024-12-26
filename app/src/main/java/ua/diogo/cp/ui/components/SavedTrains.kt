package ua.diogo.cp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.diogo.cp.R

@Composable
fun SavedTrains() {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Header("Comboios guardados")

        TrainCard(
            trainCode = "IC 721",
            origin = "Lisboa Santa Apolónia",
            destination = "Guarda",
            showImage = true
        )

        TrainCard(
            trainCode = "IC 640",
            origin = "Guarda",
            destination = "Porto",
            showImage = true
        )

        FooterButton("Ver mais / Adicionar", onClick = { /*TODO*/ })
    }
}

@Composable
fun Header(title: String) {
    Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 32.dp, top = 20.dp)
    )
}

@Composable
fun TrainCard(trainCode: String, origin: String, destination: String, showImage: Boolean) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .shadow(6.dp, RoundedCornerShape(8.dp))
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = trainCode,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            Text(
                text = "Origem: $origin",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = "Destino: $destination",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
            )
        }
        if (showImage) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cplogo),
                    contentDescription = "Delete",
                    modifier = Modifier.height(24.dp)
                )
            }
        }
    }
}

@Composable
fun FooterButton(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        ElevatedButton(
            onClick = onClick
        ) {
            Text(
                text = text
            )
        }
    }
}
