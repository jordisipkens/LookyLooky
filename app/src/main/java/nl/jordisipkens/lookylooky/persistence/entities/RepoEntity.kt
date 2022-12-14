package nl.jordisipkens.lookylooky.persistence.entities

import androidx.room.*
import nl.jordisipkens.lookylooky.network.models.Repo

@Entity
data class RepoEntity @JvmOverloads constructor(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?,
    @Embedded val owner: OwnerEntity,
    val homepage: String?,
    val language: String?,
    @ColumnInfo(name="created_at") val createdAt: String,
    @ColumnInfo(name="updated_at") val updatedAt: String,
    @ColumnInfo(name="pushed_at") val pushedAt: String,
)

// Keep in mind that it is in context of the data model, so you're passing the this to a new Entity Object.
fun Repo.toEntity(): RepoEntity {
    return RepoEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        owner = this.owner.toEntity(),
        homepage = this.homepage,
        language = this.language,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        pushedAt = this.pushedAt,
    )
}