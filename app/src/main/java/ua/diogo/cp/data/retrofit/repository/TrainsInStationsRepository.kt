package ua.diogo.cp.data.retrofit.repository

import ua.diogo.cp.data.retrofit.SecondRetrofitInstance
import ua.diogo.cp.data.retrofit.entity.TrainsInStation

class TrainsInStationsRepository {
    private val trainsInStationsService = SecondRetrofitInstance.trainsInStationsService

    suspend fun getTrainsInStations(stationId: String): List<TrainsInStation> {
        return trainsInStationsService.getStationStatus(stationId)
    }
}
