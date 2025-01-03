package ua.diogo.cp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SavedTrains(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Header("Comboios guardados")

//        TrainCard(
//            trainCode = "721",
//            origin = "Lisboa Santa Apolónia",
//            destination = "Guarda",
//            designation = "IC",
//            showImage = true
//        )
//
//        TrainCard(
//            trainCode = "640",
//            origin = "Guarda",
//            destination = "Porto",
//            designation = "IC",
//            showImage = true
//        )

        FooterButton("Ver mais / Adicionar", onClick = {
            navController.navigate("trains")
        })
    }
}