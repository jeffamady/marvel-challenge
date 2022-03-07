package com.amadydev.intermedia.data.datasource

import com.amadydev.intermedia.data.BaseDataSource
import com.amadydev.intermedia.data.services.EventsService
import javax.inject.Inject

class EventsDataSource @Inject constructor(
    private val eventsService: EventsService
) : BaseDataSource() {
    suspend fun getEvents(auth: HashMap<String, String>, orderBy: String, limit: Int) =
        getResult { eventsService.getEvents(auth, orderBy, limit) }

    suspend fun getComics(eventId: Int, auth: HashMap<String, String>, limit: Int) =
        getResult { eventsService.getComics(eventId, auth, limit) }
}