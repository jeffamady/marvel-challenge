package com.amadydev.intermedia.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadydev.intermedia.R
import com.amadydev.intermedia.data.firebase.FirebaseDb
import com.amadydev.intermedia.utils.extensions.isEmailValid
import com.amadydev.intermedia.utils.extensions.isPasswordValid
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val db: FirebaseDb) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun validateForm(email: String, password: String) {
        when {
            !email.isEmailValid() -> {
                _loginState.value = LoginState.EmailError(R.string.invalid_email)
                _loginState.value = LoginState.IsFormValid(false)
            }
            !password.isPasswordValid() -> {
                _loginState.value = LoginState.PasswordError(R.string.invalid_password)
                _loginState.value = LoginState.IsFormValid(false)
            }
            else -> {
                _loginState.value = LoginState.IsFormValid(true)
            }

        }
    }

    fun loginUser(email: String, password: String) {
        _loginState.value = LoginState.Loading(true)
        db.loginUser(this, email, password)
    }

    fun loginSuccess(isSuccess: Boolean) {
        _loginState.value = LoginState.Loading(false)
        if (isSuccess)
            _loginState.value = LoginState.Success(R.string.success)
        else
            _loginState.value = LoginState.Error
    }

    fun loginWithFacebook(activity: LoginActivity) =
        db.loginWithFacebook(this, activity)


    sealed class LoginState {
        data class Success(val resourceId: Int) : LoginState()
        data class IsFormValid(val isFormValid: Boolean) : LoginState()
        data class EmailError(val resourceId: Int) : LoginState()
        data class PasswordError(val resourceId: Int) : LoginState()
        data class Loading(val isLoading: Boolean) : LoginState()
        object Error : LoginState()
    }
}