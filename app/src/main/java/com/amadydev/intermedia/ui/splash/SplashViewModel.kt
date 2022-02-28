package com.amadydev.intermedia.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadydev.intermedia.data.firebase.FirebaseDb
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val db: FirebaseDb) : ViewModel() {
    private val _splashState = MutableLiveData<SplashState>()
    val splashState: LiveData<SplashState> = _splashState

    fun isUserLoggedIn() {
        val currentUserId = db.getCurrentUserId()
        if (currentUserId.isNotEmpty()) {
            _splashState.value = SplashState.Logged
        } else {
            _splashState.value = SplashState.NotLogged
        }
    }

    sealed class SplashState {
        object Logged : SplashState()
        object NotLogged : SplashState()
    }
}