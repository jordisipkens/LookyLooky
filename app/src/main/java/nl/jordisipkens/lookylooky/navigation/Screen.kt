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
    object Detail: RepoScreen("repos/{user}/{repo}") {
        fun createRoute(user: String, repo: String) = "repos/$user/$repo"
    }
    object RepoEvents: RepoScreen("repos/{user}/{repo}/events") {
        fun createRoute(user: String, repo: String) = "repos/$user/$repo/events"
    }
}