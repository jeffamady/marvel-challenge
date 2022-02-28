package com.amadydev.intermedia.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val comics: Appearances = Appearances(),
    val description: String = "",
    val events: Appearances = Appearances(),
    val id: Int = 0,
    val name: String = "",
    val series: Appearances = Appearances(),
    val stories: Appearances = Appearances(),
    val thumbnail: Thumbnail = Thumbnail(),
    val urls: List<Url> = listOf()
) : Parcelable

@Parcelize
data class Appearances(
    val available: Int = 0,
    val collectionURI: String = "",
    @SerializedName("items")
    val appearances: List<Appearance> = listOf(),
    val returned: Int = 0
) : Parcelable

@Parcelize
data class Appearance(
    val name: String = "",
    val resourceURI: String = "",
    val type: String = ""
) : Parcelable

@Parcelize
data class Thumbnail(
    val extension: String = "",
    val path: String = ""
) : Parcelable

@Parcelize
data class Url(
    val type: String = "",
    val url: String = ""
) : Parcelable