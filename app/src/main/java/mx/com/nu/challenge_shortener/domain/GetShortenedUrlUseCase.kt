package mx.com.nu.challenge_shortener.domain

import mx.com.nu.challenge_shortener.data.MainRepository
import mx.com.nu.challenge_shortener.domain.model.UrlShortener
import javax.inject.Inject

class GetShortenedUrlUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(url: String): UrlShortener {
        return repository.getShortenerUrlFromApi(url = url)
    }
}
