package com.amadydev.intermedia.data.services

import com.amadydev.intermedia.data.models.ApiResponse
import com.amadydev.intermedia.data.models.Character
import com.amadydev.intermedia.data.models.Comic
import com.amadydev.intermedia.data.models.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CharactersService {
    @GET("characters")
    suspend fun getCharacters(
        @QueryMap auth: HashMap<String, String>,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<ApiResponse<Data<List<Character>>>>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path("characterId") characterId: Int,
        @QueryMap auth: HashMap<String, String>,
        @Query("limit") limit: Int
    ): Response<ApiResponse<Data<List<Comic>>>>
}