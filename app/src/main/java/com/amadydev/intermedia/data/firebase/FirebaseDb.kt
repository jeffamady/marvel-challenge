package com.amadydev.intermedia.data.firebase

import android.util.Log
import com.amadydev.intermedia.ui.login.LoginActivity
import com.amadydev.intermedia.ui.login.LoginViewModel
import com.amadydev.intermedia.ui.signup.SignUpViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDb @Inject constructor() {
    private val auth = Firebase.auth
    private val callBackManager = CallbackManager.Factory.create()

    fun getCurrentUserId(): String {
        var currentUserId = ""
        auth.currentUser?.let {
            currentUserId = it.uid
        }
        return currentUserId
    }

    fun registerUser(
        viewModel: SignUpViewModel,
        name: String, email: String, password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    viewModel.userRegisteredSuccess(true)
                else
                    viewModel.userRegisteredSuccess(false)
            }
    }

    fun loginUser(viewModel: LoginViewModel, email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    viewModel.loginSuccess(true)
                else
                    viewModel.loginSuccess(false)
            }

    }

    fun loginWithFacebook(viewModel: LoginViewModel, activity: LoginActivity) {
        LoginManager.getInstance()
            .logInWithReadPermissions(activity, listOf("email"))

        LoginManager.getInstance().registerCallback(callBackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    result?.let {
                        val token = it.accessToken
                        val credential = FacebookAuthProvider.getCredential(token.token)
                        auth.signInWithCredential(credential)
                            .addOnCompleteListener { authResult ->
                                if (authResult.isSuccessful)
                                    viewModel.loginSuccess(true)
                                else
                                    viewModel.loginSuccess(false)

                            }
                    }
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException?) {
                    viewModel.loginSuccess(false)
                }

            })

    }
}
