package nl.jordisipkens.lookylooky.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.jordisipkens.lookylooky.R

@Composable
fun HomeScreen(showRepositories: (user: String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        Text(
            text = stringResource(id = R.string.pickUserDescription),
            modifier = Modifier.padding(10.dp),
            textAlign = TextAlign.Justify
        )

        Text(
            text = stringResource(id = R.string.pickUserLabel),
            modifier = Modifier.padding(10.dp)
        )

        stringArrayResource(id = R.array.usernames).forEach { username ->
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