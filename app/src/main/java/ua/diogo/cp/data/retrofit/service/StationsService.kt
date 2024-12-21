package ua.diogo.cp.data.retrofit.service

import retrofit2.http.GET
import ua.diogo.cp.data.retrofit.entity.Stations

interface StationsService {
    @GET("cp-api/siv/stations/")
    suspend fun getStations(): List<Stations>
}