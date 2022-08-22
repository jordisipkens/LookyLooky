package nl.jordisipkens.lookylooky

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.jordisipkens.lookylooky.features.home.DisplayUsers
import nl.jordisipkens.lookylooky.features.repos.RepositoriesScreen
import nl.jordisipkens.lookylooky.navigation.Screen
import nl.jordisipkens.lookylooky.ui.theme.LookyLookyTheme

@Composable
fun LookyLookyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable(route = Screen.Home.route) {
            AppBar(title = "Home", backButton = false) {
                DisplayUsers(showRepositories = { user ->
                    navController.navigate(Screen.Repos.createRoute(user))
                })
            }
        }
        composable(route = Screen.Repos.route) { backStackEntry ->
            val user = backStackEntry.arguments?.getString("user")
            requireNotNull(user) { "User parameter was not set, please set it!" }
            AppBar(title = "$user repos", backButtonAction = {navController.popBackStack()}) {
                RepositoriesScreen(user)
            }
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LookyLookyTheme {
        LookyLookyApp()
    }
}
