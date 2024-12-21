package ua.diogo.cp.data.retrofit.entity

data class Stations(
    val code: String,
    val designation: String,
    val latitude: String,
    val longitude: String,
    val region: Any,
    val railways: List<String>
)