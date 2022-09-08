package nl.jordisipkens.lookylooky.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import nl.jordisipkens.lookylooky.network.models.Owner

@Entity
data class OwnerEntity(
    val login: String,
    val url: String,
    @ColumnInfo(name="avatar_url") val avatarUrl: String,
)

fun Owner.toEntity(): OwnerEntity {
    return OwnerEntity(
        login = this.login,
        url = this.htmlUrl,
        avatarUrl = this.avatarUrl
    )
}