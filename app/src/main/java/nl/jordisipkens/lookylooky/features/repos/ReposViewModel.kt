package nl.jordisipkens.lookylooky.features.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jordisipkens.lookylooky.features.repos.ReposUiState.*
import nl.jordisipkens.lookylooky.network.repository.ReposRepository
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
    private val repository: ReposRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<ReposUiState>(Idle)

    val uiState: StateFlow<ReposUiState> = _uiState

    private lateinit var user: String

    fun setUser(user: String) {
        this.user = user
    }

    fun fetchRepos() {
        if(_uiState.value == Idle) {
            _uiState.value = Loading

            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.fetchRepos(user)
                withContext(Dispatchers.Main) {
                    _uiState.value =
                        response?.let { Loaded(it) } ?: Error("Request has no response")
                }
            }
        }
    }

    fun navigateToReposDetail() {

    }
}

sealed class ReposUiState {
    object Idle: ReposUiState()
    object Loading: ReposUiState()
    class Loaded(val data: List<RepoEntity>) : ReposUiState()
    class Error(val error: String) : ReposUiState()
}