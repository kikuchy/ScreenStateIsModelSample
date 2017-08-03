package net.kikuchy.loaddatascore.stargazer

import com.google.gson.annotations.SerializedName
import java.net.URI

data class Stargazer(
        @SerializedName("login")
        val loginName: String,
        @SerializedName("avatar_url")
        val avatarUrl: URI
)
