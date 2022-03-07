package com.amadydev.intermedia.data.repositories

import com.amadydev.intermedia.data.datasource.CharactersDataSource
import javax.inject.Inject

class CharactersRepositoryImp @Inject constructor(
    private val charactersDataSource: CharactersDataSource
) : BaseRepository(), CharactersRepository {
    override suspend fun getCharacters(offSet: Int, limit: Int) =
        charactersDataSource.getCharacters(authParams.getMap(), offSet, limit)

    override suspend fun getComics(characterId: Int, limit: Int) =
        charactersDataSource.getComics(characterId, authParams.getMap(), limit)
}