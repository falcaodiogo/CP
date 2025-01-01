package ua.diogo.cp.data.retrofit.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.diogo.cp.data.retrofit.entity.TrainsInStation

// https://www.cp.pt/sites/spring/station/trains?stationId=94-38000

interface TrainsInStationsService {
    @GET("sites/spring/station/trains")
    suspend fun getStationStatus(@Query("stationId") id: String): List<TrainsInStation>
}
