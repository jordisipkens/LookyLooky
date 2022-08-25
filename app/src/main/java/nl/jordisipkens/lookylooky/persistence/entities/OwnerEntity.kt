package nl.jordisipkens.lookylooky.persistence.entities

import androidx.room.Entity
import nl.jordisipkens.lookylooky.network.models.Owner

@Entity
data class OwnerEntity(
    val login: String,
    val url: String
)

fun Owner.toEntity(): OwnerEntity {
    return OwnerEntity(
        login = this.login,
        url = this.htmlUrl
    )
}