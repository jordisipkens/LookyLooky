package nl.jordisipkens.lookylooky.features.repos.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.jordisipkens.lookylooky.R
import nl.jordisipkens.lookylooky.persistence.entities.EventEntity
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity
import nl.jordisipkens.lookylooky.ui.theme.md_theme_light_primary
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
        is ReposDetailUiState.Empty -> Text(text = stringResource(id = R.string.repoNotFoundLabel))
        is ReposDetailUiState.Loaded -> {
            viewModel.fetchEvents()

            when (val eventState = viewModel.eventUiState.collectAsState().value) {
                is ReposEventsUiState.Idle -> ReposDetailView(repo = state.repo, infoMessage = stringResource(
                    id = R.string.eventsNotFoundLabel
                ))
                is ReposEventsUiState.Loading -> ReposDetailView(repo = state.repo, infoMessage = stringResource(
                    id = R.string.fetchingEventsLabel
                ))
                is ReposEventsUiState.Loaded -> ReposDetailView(repo = state.repo, events = eventState.data, infoMessage = "There are no events for this reposiroty")
                is ReposEventsUiState.Error -> ReposDetailView(repo = state.repo, infoMessage = eventState.error)
            }


        }

    }
}

@Composable
private fun ReposDetailView(repo: RepoEntity, events: List<EventEntity> = emptyList(), infoMessage: String) {
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
                stringResource(id = R.string.eventsLabel),
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .padding(
                        start = 5.dp,
                        bottom = 10.dp,
                        top = 5.dp
                    )
                    .fillMaxWidth(),
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

    RowWithLabel(
        label = stringResource(id = R.string.nameLabel), 
        value = repo.name
    )

    repo.description?.let {
        RowWithLabel(
            label = stringResource(id = R.string.descriptionLabel), 
            value = repo.description)
    }

    RowWithLabel(
        label = stringResource(id = R.string.ownerLabel),
        value = repo.owner.login)
    ClickableRowWithLabel(
        label = stringResource(id = R.string.ownerUrlLabel), 
        value = repo.owner.url)

    repo.homepage?.let {
        ClickableRowWithLabel(
            label = stringResource(id = R.string.homepageLabel),
            value = repo.homepage)
    }
    repo.language?.let {
        RowWithLabel(
            label = stringResource(id = R.string.languageLabel),
            value = repo.language
        )
    }
    RowWithLabel(
        label = stringResource(id = R.string.createdAtLabel),
        value =
        LocalDate.parse(repo.createdAt, formatter).toString()
    )
    RowWithLabel(
        label = stringResource(id = R.string.updatedAtLabel),
        value = LocalDate.parse(repo.updatedAt, formatter).toString()
    )
}


@Composable
fun RowWithLabel(label: String, value: String, horizontalPadding: Dp = 5.dp, verticalPadding: Dp = 2.dp) {
    Row(
        modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(label, fontWeight = FontWeight.W600)
        Text(value)
    }
}

@Composable
fun ClickableRowWithLabel(label: String, value: String, horizontalPadding: Dp = 5.dp, verticalPadding: Dp = 2.dp) {
    Row(
        modifier = Modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(label, fontWeight = FontWeight.W600)

        val uriHandler = LocalUriHandler.current
        val annotatedString = buildAnnotatedString {

            withStyle(style = SpanStyle(color = md_theme_light_primary, textDecoration = TextDecoration.Underline, fontSize = 16.sp)) {
                append(value)
            }
        }
        ClickableText(text = annotatedString,
            style = TextStyle.Default,
            onClick = {
                uriHandler.openUri(annotatedString.toString())
            },
        )
    }
}