package com.amadydev.intermedia.data.datasource

import com.amadydev.intermedia.data.BaseDataSource
import com.amadydev.intermedia.data.services.CharactersService
import javax.inject.Inject

class CharactersDataSource @Inject constructor(
    private val charactersService: CharactersService
) : BaseDataSource() {
    suspend fun getCharacters(auth: HashMap<String, String>, offSet: Int, limit: Int) =
        getResult { charactersService.getCharacters(auth, offSet, limit) }

    suspend fun getComics(characterId: Int, auth: HashMap<String, String>, limit: Int) =
        getResult { charactersService.getComics(characterId, auth, limit) }
}