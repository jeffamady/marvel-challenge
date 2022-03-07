package com.amadydev.intermedia.data.services

import com.amadydev.intermedia.data.models.ApiResponse
import com.amadydev.intermedia.data.models.Comic
import com.amadydev.intermedia.data.models.Data
import com.amadydev.intermedia.data.models.Event
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface EventsService {
    @GET("events")
    suspend fun getEvents(
        @QueryMap auth: HashMap<String, String>,
        @Query("orderBy") orderBy: String,
        @Query("limit") limit: Int
    ): Response<ApiResponse<Data<List<Event>>>>

    @GET("events/{eventId}/comics")
    suspend fun getComics(
        @Path("eventId") eventId: Int,
        @QueryMap auth: HashMap<String, String>,
        @Query("limit") limit: Int
    ): Response<ApiResponse<Data<List<Comic>>>>
}