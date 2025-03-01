package ua.diogo.cp.data.retrofit.token

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class TokenService(private val client: OkHttpClient) {

    private val tokenUrl = "https://api.cp.pt/cp-api/oauth/token"
    private val authorizationBasic = "Basic Y3AtbW9iaWxlOnBhc3M="

    fun fetchToken(): String? {
        val requestBody = "grant_type=client_credentials"
            .toRequestBody("application/x-www-form-urlencoded".toMediaType())

        val request = Request.Builder()
            .url(tokenUrl)
            .post(requestBody)
            .header("Authorization", authorizationBasic)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()

        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val jsonResponse = JSONObject(response.body?.string() ?: "")
            return jsonResponse.optString("access_token")
        }
        return null
    }
}
