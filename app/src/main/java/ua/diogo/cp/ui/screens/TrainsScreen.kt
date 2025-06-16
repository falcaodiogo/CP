package ua.diogo.cp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ua.diogo.cp.authentication.GoogleAuthUiClient
import ua.diogo.cp.data.retrofit.JorneysViewModel
import ua.diogo.cp.notifications.NotificationService
import ua.diogo.cp.ui.components.JorneysList
import ua.diogo.cp.ui.components.NoResultsMessage
import ua.diogo.cp.ui.components.ScreenTitle
import ua.diogo.cp.ui.components.TrainSearchRow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TrainsScreen(
    viewModel: JorneysViewModel,
    navController: NavController,
    googleAuthUiClient: GoogleAuthUiClient,
    notificationService: NotificationService
) {
    // if there is an argument in navController, fetch the train code
    val trainCode = remember {
        mutableStateOf(
            navController.currentBackStackEntry?.arguments?.getString("trainId") ?: ""
        )
    }
    val jorneys = viewModel.jorneys.observeAsState()
    val isLoading = viewModel.isLoading.observeAsState(false)

    Column(
        modifier = Modifier
            .padding(top = 46.dp, start = 24.dp, end = 24.dp, bottom = 116.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenTitle("Pesquise por comboio")
        TrainSearchRow(trainCode, viewModel)

        if (isLoading.value) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(24.dp))
                LoadingIndicator()
            }
        } else {
            jorneys.value?.let {
                JorneysList(jorneys = it, googleAuthUiClient, notificationService)
            } ?: NoResultsMessage()
        }
    }
}

fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}