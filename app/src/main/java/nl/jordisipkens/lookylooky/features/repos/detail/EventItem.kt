package nl.jordisipkens.lookylooky.features.repos.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.jordisipkens.lookylooky.R
import nl.jordisipkens.lookylooky.persistence.entities.EventEntity
import nl.jordisipkens.lookylooky.ui.theme.md_theme_light_background
import nl.jordisipkens.lookylooky.ui.theme.md_theme_light_onBackground


@Composable
fun EventItem(event: EventEntity) {
    ElevatedCard(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
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
                RowWithLabel(
                    label = stringResource(id = R.string.eventLabel),
                    value = event.type,
                    verticalPadding = 0.dp
                )
                RowWithLabel(
                    label = stringResource(id = R.string.eventUserLabel),
                    value = event.actor,
                    verticalPadding = 0.dp
                )
            }
        }
    }
}