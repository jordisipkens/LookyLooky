package nl.jordisipkens.lookylooky.navigation


sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Repos: Screen("{user}/repos") {
        fun createRoute(user: String) = "$user/repos"
    }
}

// Nested screens (think fragments)
sealed class RepoScreen(val route: String) {
    object Overview: RepoScreen("repos/{user}")
    object Detail: RepoScreen("repos/{user}/{repo}") {
        fun createRoute(repo: String, user: String) = "repos/$user/$repo"
    }
}