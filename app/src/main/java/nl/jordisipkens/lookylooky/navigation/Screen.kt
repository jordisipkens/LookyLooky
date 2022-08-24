package nl.jordisipkens.lookylooky.navigation


sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Repos: Screen("{user}/repos") {
        fun createRoute(user: String) = "$user/repos"
    }
}

// Nested screens (think fragments)
sealed class RepoScreen(val route: String) {
    object Overview: RepoScreen("repos/{user}") {
        fun createRoute(user: String) = "$user/repos"
    }
    object Detail: RepoScreen("repos/{repo}") {
        fun createRoute(repo: String) = "repos/$repo"
    }
    object RepoEvents: RepoScreen("repos/{repo}/events") {
        fun createRoute(repo: String) = "repos/$repo/events"
    }
}