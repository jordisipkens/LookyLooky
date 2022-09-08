package nl.jordisipkens.lookylooky.features.repos.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.jordisipkens.lookylooky.network.repository.EventRepository
import nl.jordisipkens.lookylooky.network.repository.ReposRepository
import nl.jordisipkens.lookylooky.persistence.entities.EventEntity
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity
import javax.inject.Inject

@HiltViewModel
class ReposDetailViewModel @Inject constructor(
    private val reposRepository: ReposRepository,
    private val eventRepository: EventRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<ReposDetailUiState>(ReposDetailUiState.Empty)
    val uiState: StateFlow<ReposDetailUiState> = _uiState

    private val _eventUiState = MutableStateFlow<ReposEventsUiState>(ReposEventsUiState.Idle)
    val eventUiState: StateFlow<ReposEventsUiState> = _eventUiState

    private lateinit var repositoryName: String
    private lateinit var user: String
    private lateinit var repository: RepoEntity

    fun setRepositoryName(name: String) {
        repositoryName = name
    }

    fun setUser(user: String) {
        this.user = user
    }

    fun getRepoFromCache() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = reposRepository.fetchRepoByName(repositoryName)
            withContext(Dispatchers.Main) {
                _uiState.value =
                    response?.let {
                        repository = response
                        ReposDetailUiState.Loaded(it)
                    } ?: ReposDetailUiState.Empty
            }
        }
    }

    fun fetchEvents() {
        if(_eventUiState.value == ReposEventsUiState.Idle) {
            _eventUiState.value = ReposEventsUiState.Loading

            viewModelScope.launch(Dispatchers.IO) {
                val response = eventRepository.fetchEvents(user, repository)
                withContext(Dispatchers.Main) {
                    _eventUiState.value = (response?.let { ReposEventsUiState.Loaded(it) }
                        ?: ReposEventsUiState.Error("No events found"))
                }
            }
        }
    }

}

sealed class ReposEventsUiState {
    object Idle: ReposEventsUiState()
    object Loading: ReposEventsUiState()
    class Loaded(val data: List<EventEntity>) : ReposEventsUiState()
    class Error(val error: String) : ReposEventsUiState()
}

sealed class ReposDetailUiState {
    object Empty: ReposDetailUiState()
    class Loaded(val repo: RepoEntity): ReposDetailUiState()
}