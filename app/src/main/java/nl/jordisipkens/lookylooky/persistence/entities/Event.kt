package nl.jordisipkens.lookylooky.persistence.entities

// repository events
data class Event(
    val id: String,
    val type: String,
    val actor: Actor
)