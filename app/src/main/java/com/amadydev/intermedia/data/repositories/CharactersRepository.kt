package com.amadydev.intermedia.data.repositories

import com.amadydev.intermedia.data.models.NetResult
import com.amadydev.intermedia.data.services.CharacterService
import com.amadydev.intermedia.data.services.CharactersResponse
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val characterService: CharacterService
): BaseRepository() {

    suspend fun getCharacters(offset: Int, limit: Int = 15): NetResult<CharactersResponse> =
        handleResult(characterService.getCharacters(authParams.getMap(), offset, limit))
}