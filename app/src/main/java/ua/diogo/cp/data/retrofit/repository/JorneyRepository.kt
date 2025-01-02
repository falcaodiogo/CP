package ua.diogo.cp.data.retrofit.repository

import ua.diogo.cp.data.retrofit.RetrofitInstance
import ua.diogo.cp.data.retrofit.entity.Jorney

//https://api.cp.pt/cp-api/siv/trains/528/timetable/2025-01-01

class JorneyRepository {
    private val jorneyService = RetrofitInstance.jorneyService

    suspend fun getJorney(stationId: String, date: String): Jorney {
        return jorneyService.getJorney(stationId, date)
    }
}
