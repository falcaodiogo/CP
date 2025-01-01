package ua.diogo.cp.data.retrofit.entity

data class Jorney(
    val trainNumber: Int,
    val serviceCode: TrainService,
    val delay: Int,
    val occupancy: Any,
    val latitude: String,
    val longitude: String,
    val status: String,
    val trainStops: List<TrainStop>
)