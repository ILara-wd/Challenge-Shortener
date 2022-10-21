package mx.com.nu.challenge_shortener.data

import mx.com.nu.challenge_shortener.data.model.UrlResponse
import mx.com.nu.challenge_shortener.data.network.MainService
import mx.com.nu.challenge_shortener.domain.model.UrlShortener
import mx.com.nu.challenge_shortener.domain.model.toDomain
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: MainService
) {

    suspend fun getShortenerUrlFromApi(url: String): UrlShortener {
        val response: UrlResponse? = api.getShortenerUrl(url = url)
        return response!!.toDomain()
    }

    fun getLocalUrlLis(): List<UrlShortener> = LocalUrlRepository.urlList

}
