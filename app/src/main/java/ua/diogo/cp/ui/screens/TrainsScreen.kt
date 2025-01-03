package ua.diogo.cp.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.diogo.cp.data.retrofit.JorneysViewModel
import ua.diogo.cp.data.retrofit.StationsViewModel
import ua.diogo.cp.ui.components.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TrainsScreen(
    context: Context,
    viewModel: JorneysViewModel,
    stationsViewModel: StationsViewModel
) {
    val trainCode = remember { mutableStateOf("") }
    val jorneys = viewModel.jorneys.observeAsState()
    val isLoading = viewModel.isLoading.observeAsState(false)

    Column(
        modifier = Modifier
            .padding(top = 46.dp, start = 24.dp, end = 24.dp, bottom = 116.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        ScreenTitle("Pesquisa por comboio")
        TrainSearchRow(trainCode, viewModel)

        if (isLoading.value) {
            LoadingIndicator()
        } else {
            jorneys.value?.let {
                JorneysList(jorneys = it)
            } ?: NoResultsMessage()
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
}





fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}