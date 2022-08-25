package nl.jordisipkens.lookylooky.network.models

import com.google.gson.annotations.SerializedName

data class Event(
    val id: String,
    val type: String,
    val actor: Actor?
)

data class Actor(
    val id: String,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?
)