package com.amadydev.intermedia.data.repositories

import com.amadydev.intermedia.data.Resource
import com.amadydev.intermedia.data.models.Character
import com.amadydev.intermedia.data.models.Comic
import com.amadydev.intermedia.data.models.Data

interface CharactersRepository {
    suspend fun getCharacters(
        offSet: Int, limit: Int = 15
    ): Resource<Data<List<Character>>>

    suspend fun getComics(
        characterId: Int, limit: Int = 15
    ): Resource<Data<List<Comic>>>
}