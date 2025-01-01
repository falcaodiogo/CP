package ua.diogo.cp.data.retrofit.entity

data class TrainStop(
    val station: TrainService,
    val arrival: String,
    val departure: String,
    val platform: String,
    val latitude: String,
    val longitude: String,
    val delay: Int,
    val etd: String,
    val eta: String
)