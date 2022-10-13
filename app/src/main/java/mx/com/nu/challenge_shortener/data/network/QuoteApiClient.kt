package mx.com.nu.challenge_shortener.data.network

import mx.com.nu.challenge_shortener.data.model.UrlRequest
import mx.com.nu.challenge_shortener.data.model.UrlResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface QuoteApiClient {
    @POST("api/alias")
    suspend fun getShortenerUrl(@Body url: UrlRequest): Response<UrlResponse>
}
