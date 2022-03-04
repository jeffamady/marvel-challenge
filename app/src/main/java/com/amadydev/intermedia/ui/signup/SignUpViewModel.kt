package com.amadydev.intermedia.ui.signup

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amadydev.intermedia.R
import com.amadydev.intermedia.data.firebase.FirebaseDb
import com.amadydev.intermedia.utils.extensions.isEmailValid
import com.amadydev.intermedia.utils.extensions.isPasswordValid
import com.amadydev.intermedia.utils.extensions.isSamePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val db: FirebaseDb) : ViewModel() {
    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _signUpState

    fun validateForm(name: String, email: String, password: String, confirmPassword: String) {
        when {
            TextUtils.isEmpty(name) -> {
                _signUpState.value = SignUpState.NameError(R.string.invalid_name)
                _signUpState.value = SignUpState.IsFormValid(false)
            }
            !email.isEmailValid() -> {
                _signUpState.value = SignUpState.EmailError(R.string.invalid_email)
                _signUpState.value = SignUpState.IsFormValid(false)
            }
            !password.isPasswordValid() -> {
                _signUpState.value = SignUpState.PasswordError(R.string.invalid_password)
                _signUpState.value = SignUpState.IsFormValid(false)
            }
            !password.isSamePassword(confirmPassword) -> {
                _signUpState.value = SignUpState.ConfirmPasswordError(R.string.not_same_password)
                _signUpState.value = SignUpState.IsFormValid(false)
            }
            else -> {
                _signUpState.value = SignUpState.IsFormValid(true)
            }

        }

    }

    fun registerUser(name: String, email: String, password: String) {
        _signUpState.value = SignUpState.Loading(true)
        db.registerUser(this, name, email, password)
    }

    fun userRegisteredSuccess(isSuccess: Boolean, name: String = "") {
        _signUpState.value = SignUpState.Loading(false)
        if (isSuccess) {
            db.signOutUser()
            _signUpState.value = SignUpState.Success(R.string.registered, name)
        } else
            _signUpState.value = SignUpState.Error

    }

    sealed class SignUpState {
        data class Success(val resourceId: Int, val name: String) : SignUpState()
        data class IsFormValid(val isFormValid: Boolean) : SignUpState()
        data class NameError(val resourceId: Int) : SignUpState()
        data class EmailError(val resourceId: Int) : SignUpState()
        data class PasswordError(val resourceId: Int) : SignUpState()
        data class ConfirmPasswordError(val resourceId: Int) : SignUpState()
        data class Loading(val isLoading: Boolean) : SignUpState()
        object Error : SignUpState()
    }
}