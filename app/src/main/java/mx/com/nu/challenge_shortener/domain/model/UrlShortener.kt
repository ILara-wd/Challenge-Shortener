package mx.com.nu.challenge_shortener.domain.model

import mx.com.nu.challenge_shortener.data.model.UrlResponse

data class UrlShortener(val urlShortener: String, val urlOriginal: String)

fun UrlResponse.toDomain() =
    UrlShortener(urlShortener = links?.short ?: "", urlOriginal = links?.self ?: "")