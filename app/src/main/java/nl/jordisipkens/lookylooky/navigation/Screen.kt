package nl.jordisipkens.lookylooky.navigation


sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Repos: Screen("{user}/repos") {
        fun createRoute(user: String) = "$user/repos"
    }
}

// Nested screens (think fragments)
sealed class ReposScreen(val route: String) {
    object Overview: ReposScreen("repos/{user}") {
        fun createRoute(user: String) = "$user/repos"
    }
    object Detail: ReposScreen("repos/{user}/{repo}") {
        fun createRoute(user: String, repo: String) = "repos/$user/$repo"
    }
    object RepoEvents: ReposScreen("repos/{user}/{repo}/events") {
        fun createRoute(user: String, repo: String) = "repos/$user/$repo/events"
    }
}