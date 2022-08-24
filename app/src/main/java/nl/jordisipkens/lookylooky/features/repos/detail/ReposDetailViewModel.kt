package nl.jordisipkens.lookylooky.features.repos.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jordisipkens.lookylooky.network.repository.ReposRepository
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity
import javax.inject.Inject

@HiltViewModel
class ReposDetailViewModel @Inject constructor(
    private val repository: ReposRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<ReposDetailUiState>(ReposDetailUiState.Empty)

    val uiState: StateFlow<ReposDetailUiState> = _uiState

    private lateinit var repositoryName: String

    fun setRepositoryName(name: String) {
        repositoryName = name
    }

    fun getRepoFromCache() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.fetchRepoByName(repositoryName)
            withContext(Dispatchers.Main) {
                _uiState.value =
                    response?.let { ReposDetailUiState.Loaded(it) } ?: ReposDetailUiState.Empty
            }
        }
    }
}

sealed class ReposDetailUiState {
    object Empty: ReposDetailUiState()
    class Loaded(val repo: RepoEntity): ReposDetailUiState()
}