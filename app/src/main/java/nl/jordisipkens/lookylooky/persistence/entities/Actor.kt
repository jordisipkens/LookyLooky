package nl.jordisipkens.lookylooky.persistence.entities

import com.google.gson.annotations.SerializedName

data class Actor(
    val id: String,
    @SerializedName("display_name")
    val displayName: String,
    val url: String,
    val avatar_url: String,
)
