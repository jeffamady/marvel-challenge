package com.amadydev.intermedia.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadydev.intermedia.data.Resource
import com.amadydev.intermedia.data.models.Character
import com.amadydev.intermedia.data.repositories.CharactersRepositoryImp
import com.amadydev.intermedia.utils.Constants.LIMIT_CHARACTER
import com.amadydev.intermedia.utils.Constants.START_OFF_SET
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharactersRepositoryImp
) : ViewModel() {
    private val _charactersState = MutableLiveData<CharactersState>()
    val charactersState: LiveData<CharactersState> = _charactersState
    private var mOffset = LIMIT_CHARACTER

    init {
        loadCharacters(START_OFF_SET)
    }

    private fun loadCharacters(offset: Int) {
        _charactersState.value = CharactersState.Loading(true)
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                charactersRepository.getCharacters(offset)
            }.run {
                when (status) {
                    Resource.Status.SUCCESS -> {
                        _charactersState.value = CharactersState.Loading(false)
                        _charactersState.value = data?.results?.let { CharactersState.Success(it) }
                    }
                    Resource.Status.ERROR -> {
                        _charactersState.value = CharactersState.Loading(false)
                        _charactersState.value = CharactersState.Error(resId)
                    }
                }
            }
        }
    }

    fun loadMoreCharacters() {
        loadCharacters(mOffset)
        mOffset += LIMIT_CHARACTER
    }

    // To reload where the user was
    fun retry() {
        if (mOffset == LIMIT_CHARACTER)
            loadCharacters(START_OFF_SET)
        else
            loadCharacters(mOffset)
    }

    sealed class CharactersState {
        data class Success(val characters: List<Character>) : CharactersState()
        data class Loading(val isLoading: Boolean) : CharactersState()
        data class Error(val resId: Int) : CharactersState()
    }
}