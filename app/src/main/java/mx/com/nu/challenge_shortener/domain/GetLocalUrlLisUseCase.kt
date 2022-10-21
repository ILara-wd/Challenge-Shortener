package mx.com.nu.challenge_shortener.domain

import mx.com.nu.challenge_shortener.data.MainRepository
import mx.com.nu.challenge_shortener.domain.model.UrlShortener
import javax.inject.Inject

class GetLocalUrlLisUseCase @Inject constructor(private val repository: MainRepository) {
    suspend operator fun invoke(): List<UrlShortener> {
        return repository.getLocalUrlLis()
    }
}
