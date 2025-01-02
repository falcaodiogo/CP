package ua.diogo.cp.mathFunctHelpers

import ua.diogo.cp.data.retrofit.entity.Jorney
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun nextStation(jorneys: Jorney, showTime: Boolean = true): String {
    val currentTime = LocalTime.now()
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    try {
        // Filter and process stops with valid ETAs
        val nextStop = jorneys.trainStops
            .filter { it.eta != null && it.eta.matches(Regex("\\d{2}:\\d{2}")) } // Validate ETA format
            .mapNotNull {
                try {
                    it to LocalTime.parse(it.eta, timeFormatter)
                } catch (e: Exception) {
                    null // Skip malformed times
                }
            }
            .filter { it.second.isAfter(currentTime) || it.second == currentTime } // Filter valid times
            .minByOrNull { it.second } // Find closest ETA

        // Return the station designation or fallback message
        if (nextStop != null) {
            if (showTime && nextStop.first.eta != null && nextStop.first.platform != null) {
                return (nextStop?.first?.station?.designation + " às " + nextStop?.first?.eta + " na linha " + nextStop?.first?.platform)
                    ?: "No upcoming stations"
            } else if (showTime) {
                return "Sem informação disponível."
            } else {
                return (nextStop?.first?.station?.designation + " na linha " + nextStop?.first?.platform)
                    ?: "No upcoming stations"
            }
        }
    } catch (e: Exception) {
        // Log or handle unexpected errors
        e.printStackTrace()
        return "Error determining next station"
    }
    return "No upcoming stations"
}

    fun currentStation(jorneys: Jorney): String {
        try {
            // Parse the train's current coordinates
            val trainLatitude = jorneys.latitude.toDoubleOrNull()
            val trainLongitude = jorneys.longitude.toDoubleOrNull()

            // Validate that coordinates are available
            if (trainLatitude == null || trainLongitude == null) {
                return "Train's current location is unavailable"
            }

            // Find the station with the smallest distance to the train's current location
            val closestStop = jorneys.trainStops.minByOrNull { stop ->
                val stationLat = stop.latitude.toDoubleOrNull() ?: Double.MAX_VALUE
                val stationLon = stop.longitude.toDoubleOrNull() ?: Double.MAX_VALUE
                haversine(trainLatitude, trainLongitude, stationLat, stationLon)
            }

            // Return the closest station's designation
            return (closestStop?.station?.designation + " na linha " + closestStop?.platform + ".")
                ?: "Unable to determine current station"
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error determining current station"
        }
    }

    // Helper function: Calculate Haversine distance between two points
    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371e3 // Earth radius in meters
        val phi1 = Math.toRadians(lat1)
        val phi2 = Math.toRadians(lat2)
        val deltaPhi = Math.toRadians(lat2 - lat1)
        val deltaLambda = Math.toRadians(lon2 - lon1)

        val a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
                Math.cos(phi1) * Math.cos(phi2) *
                Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R * c // Distance in meters
    }