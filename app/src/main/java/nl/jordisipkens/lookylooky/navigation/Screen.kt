package nl.jordisipkens.lookylooky.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Repos: Screen("{user}/repos") {
        fun createRoute(user: String) = "$user/repos"
    }
}