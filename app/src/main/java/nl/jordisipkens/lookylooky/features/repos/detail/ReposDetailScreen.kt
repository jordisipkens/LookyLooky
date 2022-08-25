package nl.jordisipkens.lookylooky.features.repos.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import nl.jordisipkens.lookylooky.persistence.entities.EventEntity
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity
import nl.jordisipkens.lookylooky.ui.theme.md_theme_light_background
import nl.jordisipkens.lookylooky.ui.theme.md_theme_light_onBackground
import nl.jordisipkens.lookylooky.ui.theme.md_theme_light_surfaceVariant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ReposDetailScreen(
    repositoryName: String,
    user: String,
    viewModel: ReposDetailViewModel = hiltViewModel() // Injection
) {
    viewModel.setRepositoryName(repositoryName)
    viewModel.setUser(user)
    viewModel.getRepoFromCache()

    when (val state = viewModel.uiState.collectAsState().value) {
        is ReposDetailUiState.Empty -> Text("No repo found")
        is ReposDetailUiState.Loaded -> {
            viewModel.fetchEvents()

            when (val eventState = viewModel.eventUiState.collectAsState().value) {
                is ReposEventsUiState.Idle -> SetupViews(repo = state.repo, infoMessage = "No events found")
                is ReposEventsUiState.Loading -> SetupViews(repo = state.repo, infoMessage = "Fetching events for the repository")
                is ReposEventsUiState.Loaded -> SetupViews(repo = state.repo, events = eventState.data, infoMessage = "There are no events for this reposiroty")
                is ReposEventsUiState.Error -> SetupViews(repo = state.repo, infoMessage = eventState.error)
            }


        }

    }
}

@Composable
private fun SetupViews(repo: RepoEntity, events: List<EventEntity> = emptyList(), infoMessage: String) {
    LazyColumn(
        modifier = Modifier.padding(5.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            DetailScreen(repo)
        }
        item {
            Divider(modifier = Modifier.padding(top = 10.dp))
            Text(
                "Events ",
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(
                    start = 5.dp,
                    bottom = 10.dp,
                    top = 5.dp
                ).fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }

        if(events.isEmpty()) {
            item {
                Text(infoMessage)
            }
        } else {
            items(events) { event ->
                EventItem(event)
            }
        }


    }
}
@Composable
fun DetailScreen(repo: RepoEntity) {
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
fun EventItem(event: EventEntity) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = md_theme_light_background,
            contentColor = md_theme_light_onBackground
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {

            event.avatarUrl?.let {
                AsyncImage(
                    model = event.avatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                CreateRow(
                    label = "Event: ",
                    value = event.type,
                    verticalPadding = 0.dp
                )
                CreateRow(
                    label = "By user: ",
                    value = event.actor,
                    verticalPadding = 0.dp
                )
            }
        }
    }
}



@Composable
private fun CreateRow(label: String, value: String, horizontalPadding: Dp = 5.dp, verticalPadding: Dp = 2.dp) {
    Row(
        modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(label, fontWeight = FontWeight.W600)
        Text(value)
    }
}