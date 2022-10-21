package mx.com.nu.challenge_shortener.data

import mx.com.nu.challenge_shortener.domain.model.UrlShortener

object LocalUrlRepository {

    val urlList = mutableListOf<UrlShortener>()

    fun addUrl(item: UrlShortener) {
        urlList.add(item)
    }

}
