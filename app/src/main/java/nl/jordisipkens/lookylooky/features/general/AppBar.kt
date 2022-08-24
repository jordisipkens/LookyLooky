package nl.jordisipkens.lookylooky.features.general

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, backButton: Boolean = true, backButtonAction: () -> Unit = {}, ChildView: @Composable () -> Unit) {
    Column() {
        SmallTopAppBar(
            title = { Text(text = title) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue, titleContentColor = Color.White, navigationIconContentColor = Color.White),
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