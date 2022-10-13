package mx.com.nu.challenge_shortener.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.com.nu.challenge_shortener.data.model.UrlRequest
import mx.com.nu.challenge_shortener.data.model.UrlResponse
import javax.inject.Inject

class MainService @Inject constructor(private val api: QuoteApiClient) {

    suspend fun getShortenerUrl(url: String): UrlResponse? {
        return withContext(Dispatchers.IO) {
            val response = api.getShortenerUrl(UrlRequest(url = url))
            response.body()
        }
    }

}
