package mx.com.nu.challenge_shortener.domain

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import mx.com.nu.challenge_shortener.data.LocalUrlRepository
import mx.com.nu.challenge_shortener.data.MainRepository
import mx.com.nu.challenge_shortener.domain.model.UrlShortener
import org.junit.Before
import org.junit.Test


class GetLocalUrlLisUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: MainRepository

    lateinit var useCaseTest: GetLocalUrlLisUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        useCaseTest = GetLocalUrlLisUseCase(repository)
    }

    @Test
    fun verifyEndPoint() = runBlocking {
        //Given
        coEvery { repository.getLocalUrlLis() } returns emptyList()
        //When
        useCaseTest()
        //Then
        coVerify(exactly = 1) { repository.getLocalUrlLis() }
    }


}
