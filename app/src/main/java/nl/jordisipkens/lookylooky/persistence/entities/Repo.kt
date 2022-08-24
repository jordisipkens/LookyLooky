package nl.jordisipkens.lookylooky.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Repo @JvmOverloads constructor(
    @PrimaryKey val id: Int,

    val name: String,

    val description: String,

    @SerializedName("owner.url")
    @ColumnInfo(name="owner_url") val ownerUrl: String?,

    @SerializedName("owner.id")
    @ColumnInfo(name="ownerId") var owner: Int?,

    @SerializedName("html_url")
    @ColumnInfo(name="html_url") val htmlUrl: String?,

    val homepage: String?,

    val language: String?,

    @SerializedName("created_at")
    @ColumnInfo(name="created_at") val createdAt: String,

    @SerializedName("updated_at")
    @ColumnInfo(name="updated_at") val updatedAt: String,

    @SerializedName("pushed_at")
    @ColumnInfo(name="pushed_at") val pushedAt: String,
)