package nl.jordisipkens.lookylooky

import androidx.compose.runtime.Composable
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
            DisplayUsers(showRepositories = { user ->
                navController.navigate(Screen.Repos.createRoute(user))
            })
        }
        composable(route = Screen.Repos.route) { backStackEntry ->
            val user = backStackEntry.arguments?.getString("user")
            requireNotNull(user) { "User parameter was not set, please set it!" }
            RepositoriesScreen(user, navigateUp = { navController.popBackStack() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LookyLookyTheme {
        LookyLookyApp()
    }
}
