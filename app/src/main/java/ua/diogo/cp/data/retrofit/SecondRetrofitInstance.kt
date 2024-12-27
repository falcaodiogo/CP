package ua.diogo.cp.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.diogo.cp.data.retrofit.entity.TrainsInStation

//https://www.cp.pt/sites/spring/station/trains?stationId=94-38000

object SecondRetrofitInstance {
    private const val BASE_URL = "https://www.cp.pt/"
    var client: OkHttpClient = OkHttpClient.Builder().build()
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val trainsInStationsService: TrainsInStation by lazy {
        retrofit.create(TrainsInStation::class.java)
    }
}