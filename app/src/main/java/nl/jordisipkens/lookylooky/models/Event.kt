package nl.jordisipkens.lookylooky.models

// repository events
data class Event(
    val id: String,
    val type: String,
    val actor: Actor
)