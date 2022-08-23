package nl.jordisipkens.lookylooky.features.repos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.jordisipkens.lookylooky.features.AppBar
import nl.jordisipkens.lookylooky.models.Repository
import nl.jordisipkens.lookylooky.ui.theme.LookyLookyTheme

@Composable
fun ReposScreen(user: String, viewModel: ReposViewModel = ReposViewModel(user)) {
    when (val state = viewModel.uiState.collectAsState().value) {
        is ReposUiState.Idle -> Text("No repositories found")
        is ReposUiState.Loading -> Text("$user repositories are being loaded...")
        is ReposUiState.Loaded -> RepositoriesList(state.data)
        is ReposUiState.Error -> Text(state.error)
    }
}

@Composable
fun RepositoriesList(repos: List<Repository>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(repos) { repo ->
            Divider()
            Text(repo.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val user = "WikiWiki"
    LookyLookyTheme {
        AppBar(title = "$user | Repo's") {
            ReposScreen(user)
        }
    }
}
