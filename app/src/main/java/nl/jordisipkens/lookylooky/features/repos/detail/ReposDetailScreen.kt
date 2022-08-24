package nl.jordisipkens.lookylooky.features.repos.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.jordisipkens.lookylooky.persistence.entities.EventEntity
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ReposDetailScreen(
    repositoryName: String,
    user: String,
    viewModel: ReposDetailViewModel = hiltViewModel()
) {
    viewModel.setRepositoryName(repositoryName)
    viewModel.setUser(user)
    viewModel.getRepoFromCache()

    when (val state = viewModel.uiState.collectAsState().value) {
        is ReposDetailUiState.Empty -> Text("No repo found")
        is ReposDetailUiState.Loaded -> {
            viewModel.fetchEvents()
            Column(
                modifier = Modifier.padding(5.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                DetailScreen(state.repo)
                Divider(modifier = Modifier.padding(vertical = 20.dp))
                when (val eventState = viewModel.eventUiState.collectAsState().value) {
                    is ReposEventsUiState.Idle -> Text("No events")
                    is ReposEventsUiState.Loading -> Text("Fetching events for the repository")
                    is ReposEventsUiState.Loaded -> EventsList(events = eventState.data)
                    is ReposEventsUiState.Error -> Text(eventState.error)
                }
            }
        }

    }
}

@Composable
private fun DetailScreen(repo: RepoEntity) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    CreateRow(label = "Name: ", value = repo.name)

    repo.description?.let {
        CreateRow(label = "Description: ", value = repo.description)
    }

    CreateRow(label = "Owner: ", value = repo.owner)

    repo.homepage?.let {
        CreateRow(label = "Homepage: ", value = repo.homepage)
    }
    repo.language?.let {
        CreateRow(label = "Programming language: ", value = repo.language)
    }
    CreateRow(
        label = "Created at: ", value =
        LocalDate.parse(repo.createdAt, formatter).toString()
    )
    CreateRow(
        label = "Last updated at: ",
        value = LocalDate.parse(repo.updatedAt, formatter).toString()
    )
}

@Composable
private fun EventsList(events: List<EventEntity>) {
    Text("Show Column of ${events.count()}")
    LazyColumn(verticalArrangement = Arrangement.SpaceEvenly) {
        items(events) { event ->
            Text(event.type)
            Text(event.actor)
        }
    }
}


@Composable
private fun CreateRow(label: String, value: String) {
    Row(
        modifier = Modifier.padding(5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(label, fontWeight = FontWeight.W600)
        Text(value)
    }
}