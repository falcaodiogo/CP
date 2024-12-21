package ua.diogo.cp.data.retrofit.repository

import ua.diogo.cp.data.retrofit.RetrofitInstance
import ua.diogo.cp.data.retrofit.entity.Stations

class StationsRepository {
    private val stationsService = RetrofitInstance.stationsService
    suspend fun getStations(): List<Stations> {
        return stationsService.getStations()
    }
}