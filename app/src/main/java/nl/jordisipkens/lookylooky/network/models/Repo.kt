package nl.jordisipkens.lookylooky.network.models

import com.google.gson.annotations.SerializedName

data class Repo (
    val id: Int,
    val name: String,
    val description: String?,
    val owner: Owner,
    val homepage: String?,
    val language: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("pushed_at")
    val pushedAt: String,
)

data class Owner(
    val id: Int,
    val login: String,
    @SerializedName("html_url")
    val htmlUrl: String
)