package nl.jordisipkens.lookylooky.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Repository(
    val id: String,
    val name: String,
    val description: String,
    val private: Boolean,
    @SerializedName("owner.url")
    val ownerUrl: String,
    var owner: User?,
    @SerializedName("html_url")
    val htmlUrl: String,
    val homepage: String,
    val language: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
    @SerializedName("pushed_at")
    val pushedAt: Date,
)