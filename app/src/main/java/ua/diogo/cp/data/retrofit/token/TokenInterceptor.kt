package ua.diogo.cp.data.retrofit.token

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class TokenInterceptor(private val tokenService: TokenService) : Interceptor {

    private var cachedToken: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (cachedToken == null) {
            cachedToken = tokenService.fetchToken()
        }

        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "Bearer ${cachedToken ?: ""}")
            .header("Accept", "application/json")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()

        return chain.proceed(newRequest)
    }
}
