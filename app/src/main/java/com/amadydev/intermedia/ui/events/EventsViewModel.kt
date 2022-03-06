package com.amadydev.intermedia.ui.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amadydev.intermedia.data.Resource
import com.amadydev.intermedia.data.models.Comic
import com.amadydev.intermedia.data.models.ComicDate
import com.amadydev.intermedia.data.models.EventDto
import com.amadydev.intermedia.data.repositories.EventsRepositoryImp
import com.amadydev.intermedia.utils.Constants.LIMIT_COMIC_EVENT
import com.amadydev.intermedia.utils.Constants.LIMIT_EVENT
import com.amadydev.intermedia.utils.Constants.ORDER_BY
import com.amadydev.intermedia.utils.extensions.toEventDtoList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventsRepository: EventsRepositoryImp
) : ViewModel() {
    private val _eventsState = MutableLiveData<EventsState>()
    val eventsState: LiveData<EventsState> = _eventsState
    private var mEventId = -1

    init {
        getEvents()
    }

    private fun getEvents() {
        _eventsState.value = EventsState.Loading(true)
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                eventsRepository.getEvents(ORDER_BY, LIMIT_EVENT)
            }.run {
                when (status) {
                    Resource.Status.SUCCESS -> {
                        _eventsState.value = EventsState.Loading(false)
                        _eventsState.value =
                            data?.results?.let { EventsState.Success(it.toEventDtoList()) }
                    }
                    Resource.Status.ERROR -> {
                        _eventsState.value = EventsState.Loading(false)
                        _eventsState.value = EventsState.Error(resId)
                    }
                }
            }
        }
    }

    fun getComics(event: EventDto) {
        if (event.id != mEventId && event.isVisible) {
            _eventsState.value = EventsState.Loading(true)
            viewModelScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    eventsRepository.getComics(event.id, LIMIT_COMIC_EVENT)
                }.run {
                    when (status) {
                        Resource.Status.SUCCESS -> {
                            _eventsState.value = EventsState.Loading(false)
                            _eventsState.value = data?.results?.let {
                                EventsState.ComicSuccess(
                                    it.ifEmpty {
                                        listOf(
                                            Comic(
                                                -1,
                                                "Sorry! no comics yet",
                                                listOf(ComicDate("", ""))
                                            )
                                        )
                                    }
                                )
                            }
                            mEventId = event.id
                        }
                        Resource.Status.ERROR -> {
                            _eventsState.value = EventsState.Loading(false)
                            _eventsState.value = EventsState.Error(resId)
                        }
                    }
                }
            }
        }

    }


    fun retry() = getEvents()

    sealed class EventsState {
        data class Success(val events: List<EventDto>) : EventsState()
        data class Loading(val isLoading: Boolean) : EventsState()
        data class Error(val resId: Int) : EventsState()
        data class ComicSuccess(val comics: List<Comic>) : EventsState()
    }
}