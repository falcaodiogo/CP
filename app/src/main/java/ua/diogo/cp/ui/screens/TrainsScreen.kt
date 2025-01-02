import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.location.LocationServices
import ua.diogo.cp.data.retrofit.JorneysViewModel
import ua.diogo.cp.data.retrofit.StationsViewModel
import ua.diogo.cp.data.retrofit.entity.Jorney
import ua.diogo.cp.mathFunctHelpers.currentStation
import ua.diogo.cp.mathFunctHelpers.nextStation
import ua.diogo.cp.ui.components.DelayInfoRow
import ua.diogo.cp.ui.components.RequestLocationPermissionUsingRememberLauncherForActivityResult
import ua.diogo.cp.ui.components.TrainInfo
import ua.diogo.cp.ui.components.findNearestStation
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
    val userLocation = remember { mutableStateOf<Location?>(null) }

//    RequestLocationPermissionUsingRememberLauncherForActivityResult(
//        onPermissionGranted = {
//            fetchUserLocation(context) { location ->
////                userLocation.value = location
//                println("User location: ${location?.latitude}, ${location?.longitude}")
//            }
//        },
//        onPermissionDenied = {
//            // Handle denied permission gracefully
//        }
//    )

    Column(
        modifier = Modifier
            .padding(top = 46.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxSize()
    ) {
        ScreenTitle("Comboio")
        Spacer(modifier = Modifier.padding(8.dp))
        TrainSearchRow(trainCode, viewModel)
        Spacer(modifier = Modifier.padding(16.dp))

        userLocation.value?.let { location ->
            Text("Current Location: (${location.latitude}, ${location.longitude})")
        }

        if (isLoading.value) {
            LoadingIndicator()
        } else {
            jorneys.value?.let {
                JorneysList(jorneys = it)
            } ?: NoResultsMessage()
        }

        userLocation.value?.let {
            println("User locationmmm: ${it.latitude}, ${it.longitude}")
            NearestStationScreen(
                viewModel = stationsViewModel,
                userLatitude = it.latitude,
                userLongitude = it.longitude
            )
        }
    }
}

@Composable
fun NearestStationScreen(
    viewModel: StationsViewModel,
    userLatitude: Double,
    userLongitude: Double
) {
    val stations = viewModel.stations.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchStations()
    }

    stations.value?.let {
        val nearestStation = findNearestStation(userLatitude, userLongitude, it)
        Text(text = "Nearest Station: ${nearestStation?.designation ?: "Not Found"}")
    } ?: Text(text = "Loading stations...")
}


@SuppressLint("MissingPermission")
fun fetchUserLocation(context: Context, onLocationFetched: (Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        onLocationFetched(location)
    }.addOnFailureListener {
        onLocationFetched(null)
    }
}

@Composable
fun ScreenTitle(title: String) {
    Text(title, fontSize = 24.sp)
}

@Composable
fun TrainSearchRow(trainCode: MutableState<String>, viewModel: JorneysViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.width(250.dp),
            value = trainCode.value,
            onValueChange = { trainCode.value = it },
            placeholder = { Text("Código do comboio") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {
            viewModel.fetchJorneys(trainCode.value, getCurrentDate())
        }) {
            Icon(Icons.Rounded.Search, contentDescription = "Comboio")
        }
    }
}

@Composable
fun LoadingIndicator() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text("", fontSize = 16.sp, modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun JorneysList(jorneys: Jorney) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        JorneyHeader(jorneys)
        if (jorneys.delay != 0) {
            DelayInfoRow(delay = jorneys.delay, true)
        } else {
            DelayInfoRow(delay = jorneys.delay, true, MaterialTheme.colorScheme.primaryContainer)
        }
    }
    Spacer(modifier = Modifier.padding(16.dp))

    if (jorneys.status == "IN_TRANSIT") {
        InfoNextStation(text = "Próxima paragem: ${nextStation(jorneys)}")
    } else if (jorneys.status == "AT_STATION") {
        InfoNextStation(text = "Encontra-se parado em: ${currentStation(jorneys)}")
    } else if (jorneys.status == "COMPLETED") {
        InfoNextStation(text = "Comboio chegou ao destino às ${jorneys.trainStops[jorneys.trainStops.size - 1].arrival}")
    } else if (jorneys.status == "NEAR_NEXT") {
        InfoNextStation(text = "A dar entrada em: ${nextStation(jorneys, false)}")
    } else if (jorneys.status == null || jorneys.status == "NOT_STARTED" || jorneys.status == "AT_ORIGIN") {
        InfoNextStation(text = "Por partir.\nSairá às ${jorneys.trainStops[0].etd} na plataforma ${jorneys.trainStops[0].platform}.")
    }
    // estado suprimido?
//    RequestLocationPermissionUsingRememberLauncherForActivityResult(onPermissionGranted = { /*TODO*/ }) {
//    }
}

@Composable
fun InfoNextStation(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
fun JorneyHeader(jorneys: Jorney) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "${jorneys.serviceCode.designation} ${jorneys.trainNumber}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp)
            )
            TrainInfo("Origem", jorneys.trainStops[0].station.designation, 100)
            TrainInfo(
                "Destino",
                jorneys.trainStops[jorneys.trainStops.size - 1].station.designation,
                100
            )
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun NoResultsMessage() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text("Sem resultados.", fontSize = 16.sp, modifier = Modifier.padding(16.dp))
    }
}

fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}