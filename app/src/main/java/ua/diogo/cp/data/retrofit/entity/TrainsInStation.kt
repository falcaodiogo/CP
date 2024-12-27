package ua.diogo.cp.data.retrofit.entity

data class TrainsInStation(
    val delay: Int,
    val trainOrigin: Train,
    val trainDestination: Train,
    val departureTime: String,
    val arrivalTime: String,
    val trainNumber: Int,
    val trainService: TrainService,
    val platform: String,
    val occupancy: Any,
    val eta: String,
    val etd: String
) {
    override fun toString(): String {
        return super.toString()
    }
}