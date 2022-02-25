package com.amadydev.intermedia.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadydev.intermedia.data.models.Character
import com.amadydev.intermedia.data.models.NetResult
import com.amadydev.intermedia.data.repositories.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository
) : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> get() = _characters

    init {
        loadCharacters(0)
    }

    private fun loadCharacters(offset: Int) {
        viewModelScope.launch {
            when (val response = charactersRepository.getCharacters(offset)) {
                is NetResult.Success -> {
                    _characters.postValue(response.data.charactersList.characters)
                }
                is NetResult.Error -> {
                    // TODO complete
                }
            }
        }
    }

    fun loadMoreCharacters() {
        // TODO complete
    }
}