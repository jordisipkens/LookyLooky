package nl.jordisipkens.lookylooky.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import nl.jordisipkens.lookylooky.network.models.Event

@Entity
data class EventEntity(
    @PrimaryKey val id: String,
    val type: String,
    val actor: String,
    @ColumnInfo(name="repo_id") val repoId: Int
)

// Keep in mind that it is in context of the data model, so you're passing the this to a new Entity Object.
fun Event.toEntity(repoId: Int): EventEntity {
    return EventEntity(
        id = this.id,
        type = this.type,
        actor = this.actor?.login ?: "Deleted",
        repoId = repoId
    )
}