package com.amadydev.intermedia.data.repositories

import com.amadydev.intermedia.data.Resource
import com.amadydev.intermedia.data.models.Comic
import com.amadydev.intermedia.data.models.Data
import com.amadydev.intermedia.data.models.Event
import com.amadydev.intermedia.utils.Constants.LIMIT_EVENT

interface EventsRepository {
    suspend fun getEvents(
        orderBy: String,
        limit: Int = LIMIT_EVENT
    ): Resource<Data<List<Event>>>

    suspend fun getComics(
        eventId: Int, limit: Int
    ): Resource<Data<List<Comic>>>
}