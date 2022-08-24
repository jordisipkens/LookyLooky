package nl.jordisipkens.lookylooky.features.general

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import nl.jordisipkens.lookylooky.ui.theme.md_theme_light_onPrimary
import nl.jordisipkens.lookylooky.ui.theme.md_theme_light_primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, backButton: Boolean = true, backButtonAction: () -> Unit = {}, ChildView: @Composable () -> Unit) {
    Column() {
        SmallTopAppBar(
            title = { Text(text = title) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = md_theme_light_primary, titleContentColor = md_theme_light_onPrimary, navigationIconContentColor = md_theme_light_onPrimary),
            navigationIcon = {
                if (backButton)
                    IconButton(
                        onClick = { backButtonAction() }
                    )
                    {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
            }
        )
        ChildView()
    }
}