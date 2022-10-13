package mx.com.nu.challenge_shortener.domain

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import mx.com.nu.challenge_shortener.data.MainRepository
import mx.com.nu.challenge_shortener.domain.model.UrlShortener
import org.junit.Before
import org.junit.Test

class GetShortenedUrlUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: MainRepository

    lateinit var useCaseTest: GetShortenedUrlUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        useCaseTest = GetShortenedUrlUseCase(repository)
    }

    @Test
    fun verifyEndPoint() = runBlocking {
        //Given
        coEvery { repository.getShortenerUrlFromApi("https://nu.com.mx/") } returns UrlShortener(
            "https://url-shortener-nu.herokuapp.com/short/34416",
            "https://nu.com.mx/"
        )
        //When
        useCaseTest("https://nu.com.mx/")
        //Then
        coVerify(exactly = 1) { repository.getShortenerUrlFromApi("https://nu.com.mx/") }
    }

}
