package nl.jordisipkens.lookylooky.features.repos.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity
import okhttp3.internal.format
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

@Composable
fun ReposDetailScreen(
    repositoryName: String,
    viewModel: ReposDetailViewModel = hiltViewModel()
) {
    viewModel.setRepositoryName(repositoryName)
    viewModel.getRepoFromCache()

    when (val state = viewModel.uiState.collectAsState().value) {
        is ReposDetailUiState.Empty -> Text("No repo found")
        is ReposDetailUiState.Loaded -> DetailScreen(state.repo)
    }
}

@SuppressLint("NewApi")
@Composable
private fun DetailScreen(repo: RepoEntity) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    Column(
        modifier = Modifier.padding(5.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
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