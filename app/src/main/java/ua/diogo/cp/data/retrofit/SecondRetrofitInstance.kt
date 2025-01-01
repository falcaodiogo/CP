package ua.diogo.cp.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.diogo.cp.data.retrofit.service.TrainsInStationsService

//https://www.cp.pt/sites/spring/station/trains?stationId=94-38000

object SecondRetrofitInstance {
    private const val BASE_URL = "https://www.cp.pt/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val trainsInStationsService: TrainsInStationsService by lazy {
        retrofit.create(TrainsInStationsService::class.java)
    }
}