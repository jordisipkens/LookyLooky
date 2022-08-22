package nl.jordisipkens.lookylooky.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DisplayUsers(showRepositories: (user: String) -> Unit) {
    val viewModel = HomeViewModel()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        Text(
            viewModel.appDescriptionText,
            modifier = Modifier.padding(10.dp),
            textAlign = TextAlign.Justify
        )

        Text(
            viewModel.pickUsernameText,
            modifier = Modifier.padding(10.dp)
        )

        viewModel.usernames.forEach { username ->
            Button(onClick = { showRepositories(username) })
            {
                Text(
                    username,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp)
                )
            }
            Divider(color = Color.Transparent, modifier = Modifier.padding(5.dp))
        }
    }
}