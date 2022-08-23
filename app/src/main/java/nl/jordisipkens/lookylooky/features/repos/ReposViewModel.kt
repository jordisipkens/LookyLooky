package nl.jordisipkens.lookylooky.features.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jordisipkens.lookylooky.features.repos.ReposUiState.*
import nl.jordisipkens.lookylooky.models.Repository
import nl.jordisipkens.lookylooky.network.repository.ReposRepository

class ReposViewModel(val user: String): ViewModel() {
    private val _uiState = MutableStateFlow<ReposUiState>(Idle)
    private val repository = ReposRepository(user)

    val uiState: StateFlow<ReposUiState> = _uiState

    init {
        fetchRepos()
    }


    private fun fetchRepos() {
        if(_uiState.value == Idle) {
            _uiState.value = Loading

            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.fetchRepos()
                withContext(Dispatchers.Main) {
                    _uiState.value =
                        response?.let { Loaded(it) } ?: Error("Request has no response")
                }
            }
        }
    }
}


sealed class ReposUiState {
    object Idle: ReposUiState()
    object Loading: ReposUiState()
    class Loaded(val data: List<Repository>) : ReposUiState()
    class Error(val error: String) : ReposUiState()
}