package mx.com.nu.challenge_shortener.ui.listUrl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.com.nu.challenge_shortener.domain.GetShortenedUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.com.nu.challenge_shortener.domain.GetLocalUrlLisUseCase
import mx.com.nu.challenge_shortener.domain.IsUrlValid
import mx.com.nu.challenge_shortener.domain.model.UrlShortener
import javax.inject.Inject

@HiltViewModel
class ListUrlViewModel @Inject constructor(
    private val getLocalUrlLisUseCase: GetLocalUrlLisUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Init)
    val state: StateFlow<State> get() = _state

    fun onExecute() {
        viewModelScope.launch {
            _state.value = State.Loading
            val result = getLocalUrlLisUseCase()
            if (result.isNotEmpty()) {
                _state.value = State.Success(result)
            } else {
                _state.value = State.Failure
            }
        }
    }

    sealed class State {
        object Init : State()
        object Loading : State()
        data class Success(val urlShortenerList: List<UrlShortener>) : State()
        object Failure : State()
    }

}
