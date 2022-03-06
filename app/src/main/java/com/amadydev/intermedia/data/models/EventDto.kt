package com.amadydev.intermedia.data.models

data class EventDto(
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail,
    val date: String,
    var isVisible: Boolean = false
)