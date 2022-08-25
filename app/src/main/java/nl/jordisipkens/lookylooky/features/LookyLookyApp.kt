package nl.jordisipkens.lookylooky.features

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import nl.jordisipkens.lookylooky.features.general.AppBar
import nl.jordisipkens.lookylooky.features.home.HomeScreen
import nl.jordisipkens.lookylooky.features.repos.ReposScreen
import nl.jordisipkens.lookylooky.features.repos.detail.ReposDetailScreen
import nl.jordisipkens.lookylooky.navigation.RepoScreen
import nl.jordisipkens.lookylooky.navigation.Screen
import nl.jordisipkens.lookylooky.ui.theme.LookyLookyTheme

@Composable
fun LookyLookyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        addHomeGraph(navController)
        addReposGraph(navController)
    }
}

private fun NavGraphBuilder.addHomeGraph(navController: NavController) {
    composable(route = Screen.Home.route) {
        AppBar(title = "Home", backButton = false) {
            HomeScreen(showRepositories = { user ->
                navController.navigate(Screen.Repos.createRoute(user))
            })
        }
    }
}

private fun NavGraphBuilder.addReposGraph(navController: NavController) {
    navigation(route = Screen.Repos.route, startDestination = RepoScreen.Overview.route) {
        composable(route = RepoScreen.Overview.route) { backStackEntry ->
            val user = backStackEntry.arguments?.getString("user")
            // Check if user has been set for this route, otherwise you can't fetch repos
            requireNotNull(user) { "User argument was not set, make sure to set it!" }
            AppBar(title = "$user repos", backButtonAction = {navController.popBackStack()}) {
                ReposScreen(user) { repo -> navController.navigate(RepoScreen.Detail.createRoute(repo = repo, user = user)) }
            }
        }
        composable(route = RepoScreen.Detail.route) { backStackEntry ->
            val user = backStackEntry.arguments?.getString("user")
            val repository = backStackEntry.arguments?.getString("repo")
            // Check if user and repo has been set.
            requireNotNull(user) { "User argument was not set, make sure to set it!" }
            requireNotNull(repository) { "Repo argument not set, make sure to set it"}
            AppBar(title = "$repository", backButtonAction = { navController.popBackStack()}) {
                ReposDetailScreen(user = user, repositoryName = repository)
            }
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
