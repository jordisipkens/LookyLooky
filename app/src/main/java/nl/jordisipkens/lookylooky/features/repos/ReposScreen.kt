package nl.jordisipkens.lookylooky.features.repos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RepositoriesScreen(user: String, navigateUp: () -> Unit) {
    val viewModel = ReposViewModel(user = user)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text("Dit is het scherm voor de repos van: ${viewModel.user}")
        Divider()
        Button(onClick = navigateUp) {
            Text("Back")
        }
    }
}