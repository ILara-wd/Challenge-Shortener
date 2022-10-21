package mx.com.nu.challenge_shortener.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import mx.com.nu.challenge_shortener.domain.GetShortenedUrlUseCase
import mx.com.nu.challenge_shortener.domain.IsUrlValid
import mx.com.nu.challenge_shortener.domain.model.UrlShortener
import mx.com.nu.challenge_shortener.ui.main.MainViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @RelaxedMockK
    private lateinit var getShortenedUrlUseCase: GetShortenedUrlUseCase

    @RelaxedMockK
    private lateinit var isUrlValid: IsUrlValid

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(getShortenedUrlUseCase, isUrlValid)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun testApiFetchDataFailure() = runTest {
        //Given
        coEvery { getShortenedUrlUseCase("https://nu.com.mx/") } returns UrlShortener("", "")

        //When
        viewModel.onExecute("https://nu.com.mx/")

        //Then
        assert(
            viewModel.state.value == MainViewModel.State.Failure
        )
    }

    @Test
    fun testApiFetchDataSuccess() = runTest {
        //Given
        coEvery { getShortenedUrlUseCase("https://nu.com.mx/") } returns UrlShortener(
            "https://url-shortener-nu.herokuapp.com/short/34416",
            "https://nu.com.mx/"
        )

        //When
        viewModel.onExecute("https://nu.com.mx/")

        //Then
        assert(
            viewModel.state.value == MainViewModel.State.Success(
                UrlShortener(
                    "https://url-shortener-nu.herokuapp.com/short/",
                    "https://nu.com.mx/"
                )
            )
        )

    }
}