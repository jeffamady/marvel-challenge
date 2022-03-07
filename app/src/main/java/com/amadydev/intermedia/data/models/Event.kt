package com.amadydev.intermedia.data.models

import com.google.gson.annotations.SerializedName

data class Event(
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail,
    @SerializedName("start") val date: String?
)
