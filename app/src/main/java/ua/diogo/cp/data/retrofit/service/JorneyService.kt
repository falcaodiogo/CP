package ua.diogo.cp.data.retrofit.service

import retrofit2.http.GET
import retrofit2.http.Path
import ua.diogo.cp.data.retrofit.entity.Jorney

//https://api.cp.pt/cp-api/siv/trains/528/timetable/2025-01-01

interface JorneyService {
    @GET("cp-api/siv/trains/{stationId}/timetable/{date}")
    suspend fun getJorney(
        @Path("stationId") stationId: String,
        @Path("date") date: String
    ): Jorney
}