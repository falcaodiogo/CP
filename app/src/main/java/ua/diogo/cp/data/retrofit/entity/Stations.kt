package ua.diogo.cp.data.retrofit.entity

data class Stations(
    val code: String,
    val designation: String,
    val latitude: String,
    val longitude: String,
    val region: Any,
    val railways: List<String>
) {
    override fun toString(): String {
        return designation
    }

    operator fun get(index: Int): Any {
        return designation
    }
}