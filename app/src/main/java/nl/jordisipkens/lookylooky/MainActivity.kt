package nl.jordisipkens.lookylooky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import nl.jordisipkens.lookylooky.features.LookyLookyApp
import nl.jordisipkens.lookylooky.ui.theme.LookyLookyTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LookyLookyTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LookyLookyApp()
                }
            }
        }
    }
}

