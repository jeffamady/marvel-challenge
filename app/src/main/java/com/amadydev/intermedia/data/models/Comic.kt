package com.amadydev.intermedia.data.models

data class Comic(
    val id: Int,
    val title: String,
    val dates: List<ComicDate>
)

data class ComicDate(
    val type: String,
    val date: String
)