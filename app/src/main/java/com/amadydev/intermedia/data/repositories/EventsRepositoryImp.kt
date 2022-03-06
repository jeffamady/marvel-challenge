package com.amadydev.intermedia.data.repositories

import com.amadydev.intermedia.data.datasource.EventsDataSource
import javax.inject.Inject

class EventsRepositoryImp @Inject constructor(
    private val eventsDataSource: EventsDataSource
) : BaseRepository(), EventsRepository {
    override suspend fun getEvents(orderBy: String, limit: Int) =
        eventsDataSource.getEvents(authParams.getMap(), orderBy, limit)

    override suspend fun getComics(eventId: Int, limit: Int) =
        eventsDataSource.getComics(eventId, authParams.getMap(), limit)
}