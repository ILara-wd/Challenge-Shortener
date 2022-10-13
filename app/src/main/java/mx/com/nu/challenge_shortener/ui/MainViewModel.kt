package mx.com.nu.challenge_shortener.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.com.nu.challenge_shortener.domain.GetShortenedUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.com.nu.challenge_shortener.domain.IsUrlValid
import mx.com.nu.challenge_shortener.domain.model.UrlShortener
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getShortenedUrlUseCase: GetShortenedUrlUseCase,
    private val isUrlValid: IsUrlValid
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Init)
    val state: StateFlow<State> get() = _state

    fun validateUrl(url: String) = viewModelScope.launch {
        _state.value = State.ValidUrl(isUrlValid(url))
    }

    fun onExecute(url: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            val result = getShortenedUrlUseCase(url)
            if (result.urlShortener.isNotEmpty()) {
                _state.value = State.Success(result)
            } else {
                _state.value = State.Failure
            }
        }
    }

    sealed class State {
        data class ValidUrl(val isValidUrl: Boolean) : State()
        object Init : State()
        object Loading : State()
        data class Success(val urlShortener: UrlShortener) : State()
        object Failure : State()
    }

}
