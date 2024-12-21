package ua.diogo.cp.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.diogo.cp.data.retrofit.service.StationsService
import ua.diogo.cp.data.retrofit.token.TokenInterceptor


object RetrofitInstance {
    private const val BASE_URL = "https://api.cp.pt/"
    var interceptor: TokenInterceptor = TokenInterceptor()
    var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val stationsService: StationsService by lazy {
        retrofit.create(StationsService::class.java)
    }
}