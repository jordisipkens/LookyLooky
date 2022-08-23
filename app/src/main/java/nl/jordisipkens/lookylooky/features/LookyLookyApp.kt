package nl.jordisipkens.lookylooky.features

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import nl.jordisipkens.lookylooky.features.home.DisplayUsers
import nl.jordisipkens.lookylooky.features.repos.ReposScreen
import nl.jordisipkens.lookylooky.navigation.RepoScreen
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
        navigation(route = Screen.Repos.route, startDestination = RepoScreen.Overview.route) {
            composable(route = RepoScreen.Overview.route) { backStackEntry ->
                val user = backStackEntry.arguments?.getString("user")
                // Check if user has been set for this route, otherwise you can't fetch repos
                requireNotNull(user) { "User argument was not set, make sure to set it!" }
                AppBar(title = "$user repos", backButtonAction = {navController.popBackStack()}) {
                    ReposScreen(user)
                }
            }
            composable(route = RepoScreen.Detail.route) { backStackEntry ->
                val user = backStackEntry.arguments?.getString("user")
                val repository = backStackEntry.arguments?.getString("repo")
                // Check if user and repo has been set.
                requireNotNull(user) { "User argument was not set, make sure to set it."}
                requireNotNull(repository) { "Repo argument not set, make sure to set it"}
                AppBar(title = "$user | $repository", backButtonAction = { navController.popBackStack()}) {
                    Text("Hello")
                }
            }
            composable(route = RepoScreen.RepoEvents.route) { backStackEntry ->
                val user = backStackEntry.arguments?.getString("user")
                val repository = backStackEntry.arguments?.getString("repo")
                // Check if user and repo has been set.
                requireNotNull(user) { "User argument was not set, make sure to set it."}
                requireNotNull(repository) { "Repo argument not set, make sure to set it"}
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
