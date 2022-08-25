package nl.jordisipkens.lookylooky.features.repos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.jordisipkens.lookylooky.features.general.AppBar
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity
import nl.jordisipkens.lookylooky.ui.theme.*

private lateinit var reposViewModel: ReposViewModel
@Composable
fun ReposScreen(user: String, viewModel: ReposViewModel = hiltViewModel(), onItemClicked: (repository: String) -> Unit) {
    reposViewModel = viewModel
    reposViewModel.setUser(user)
    reposViewModel.fetchRepos()

    when (val state = viewModel.uiState.collectAsState().value) {
        is ReposUiState.Idle -> Text("No repositories found")
        is ReposUiState.Loading -> Text("$user repositories are being loaded...")
        is ReposUiState.Loaded -> RepositoriesList(state.data, onItemClicked)
        is ReposUiState.Error -> Text(state.error)
    }
}

@Composable
private fun RepositoriesList(repos: List<RepoEntity>, onItemClicked: (repository: String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(repos) { repo ->
            ItemCard(repoEntity = repo, onItemClicked)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemCard(repoEntity: RepoEntity, onItemClicked: (repository: String) -> Unit) {
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = md_theme_light_background, contentColor = md_theme_light_onBackground),
            border = BorderStroke(1.dp, Color.Gray),
            onClick = { onItemClicked(repoEntity.name) }
        ) {
            Text(
                repoEntity.name,
                fontSize = 25.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(start = 10.dp, top = 5.dp, end = 5.dp, bottom = 0.dp))
            repoEntity.description?.let { Text(
                it,
                color = Color.Gray, 
                modifier = Modifier.padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 5.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            ) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val user = "WikiWiki"
    LookyLookyTheme {
        AppBar(title = "$user | Repo's") {
            RepositoriesList(repos = listOf(repoEntity1, repoEntity2)){}
        }
    }
}


/* DEBUG */
private val repoEntity1 = RepoEntity(
    id = 1,
    name = "Repo 1",
    description = "This is a descprtion",
    owner ="Godrick",
    homepage = "",
    language = "",
    updatedAt = "",
    createdAt = "",
    pushedAt = ""
)
private val repoEntity2 = RepoEntity(
    id = 1,
    name = "LookyLooky",
    description = "This is a descprtion",
    owner ="Godrick",
    homepage = "",
    language = "",
    updatedAt = "",
    createdAt = "",
    pushedAt = ""
)