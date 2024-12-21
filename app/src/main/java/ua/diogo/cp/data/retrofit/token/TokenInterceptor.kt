package ua.diogo.cp.data.retrofit.token

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class TokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "Bearer 64bfb263-e244-4dfd-9f14-5929160775c8")
            .header("Accept", "application/json")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()

        return chain.proceed(newRequest)
    }
}