package com.amadydev.intermedia.ui.characters.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadydev.intermedia.data.Resource
import com.amadydev.intermedia.data.models.Comic
import com.amadydev.intermedia.data.repositories.CharactersRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val charactersRepository: CharactersRepositoryImp
) : ViewModel() {
    private val _comicsState = MutableLiveData<ComicsState>()
    val comicState: LiveData<ComicsState> = _comicsState

    fun getComics(characterId: Int) {
        _comicsState.value = ComicsState.Loading(true)
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                charactersRepository.getComics(characterId)
            }.run {
                when (status) {
                    Resource.Status.SUCCESS -> {
                        _comicsState.value = ComicsState.Loading(false)
                        _comicsState.value = data?.results?.let { ComicsState.Success(it) }
                    }
                    Resource.Status.ERROR -> {
                        _comicsState.value = ComicsState.Loading(false)
                        _comicsState.value = ComicsState.Error(resId)
                    }
                }
            }
        }
    }

    sealed class ComicsState {
        data class Success(val comics: List<Comic>) : ComicsState()
        data class Loading(val isLoading: Boolean) : ComicsState()
        data class Error(val resId: Int) : ComicsState()
    }
}