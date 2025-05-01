import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@Composable
fun LocationTracker(
    onRequestPermission: () -> Unit
) {
    val context = LocalContext.current
    var location by remember { mutableStateOf<Location?>(null) }
    var permissionGranted by remember { mutableStateOf(false) }

    val locationHelper = remember { LocationHelper(context) }

    // Check permissions when composable launches
    LaunchedEffect(Unit) {
        permissionGranted = locationHelper.hasLocationPermission()
    }

    // Request location updates when permission is granted
    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            locationHelper.requestLocationUpdates { newLocation ->
                location = newLocation
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!permissionGranted) {
            Text("Location permission required")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRequestPermission) {
                Text("Request Permission")
            }
        } else if (location == null) {
            Text("Fetching location...")
            CircularProgressIndicator()
        } else {
            Text(
                "Your current location:",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Latitude: ${location?.latitude ?: 0.0}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                "Longitude: ${location?.longitude ?: 0.0}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                locationHelper.requestLocationUpdates { newLocation ->
                    location = newLocation
                }
            }) {
                Text("Refresh Location")
            }
        }
    }
}

class LocationHelper(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationUpdates(
        onLocationUpdate: (Location) -> Unit
    ) {
        if (!hasLocationPermission()) return

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled && !isNetworkEnabled) {
            // Handle case where location services are disabled
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let { onLocationUpdate(it) }
            }
    }
}