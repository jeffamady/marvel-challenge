package com.amadydev.intermedia.data.services

import com.google.gson.annotations.SerializedName
import com.amadydev.intermedia.data.models.Character
import retrofit2.Response
import retrofit2.http.*

interface CharacterService {

    @GET("characters")
    suspend fun getCharacters(
        @QueryMap auth: HashMap<String, String>,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<CharactersResponse>
}

data class CharactersResponse(
    val code: Int = 0,
    @SerializedName("data")
    val charactersList: CharactersList
)

data class CharactersList(
    @SerializedName("results")
    val characters: List<Character>
)