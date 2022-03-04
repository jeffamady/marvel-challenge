package com.amadydev.intermedia.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.amadydev.intermedia.R
import com.amadydev.intermedia.databinding.ActivityLoginBinding
import com.amadydev.intermedia.ui.base.BaseActivity
import com.amadydev.intermedia.ui.main.MainScreenActivity
import com.amadydev.intermedia.ui.signup.SignUpActivity
import com.amadydev.intermedia.utils.extensions.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validateData()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        loginViewModel.loginState.observe(this) {
            with(binding) {
                when (it) {
                    is LoginViewModel.LoginState.EmailError ->
                        etEmail.error = getString(it.resourceId)
                    is LoginViewModel.LoginState.PasswordError ->
                        etPassword.error = getString(it.resourceId)
                    is LoginViewModel.LoginState.IsFormValid -> {
                        btnLogin.isEnabled = it.isFormValid
                        if (it.isFormValid)
                            btnLogin.setTextColor(Color.WHITE)
                        else
                            btnLogin.setTextColor(Color.BLACK)
                    }
                    is LoginViewModel.LoginState.Loading ->
                        showProgressDialog(it.isLoading)
                    is LoginViewModel.LoginState.Success -> {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(it.resourceId),
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@LoginActivity, MainScreenActivity::class.java))
                        finish()
                    }
                    LoginViewModel.LoginState.Error ->
                        showErrorSnackBar(root, getString(R.string.login_form_error))
                }
            }
        }
    }

    private fun validateData() {
        with(binding) {
            etEmail.afterTextChanged {
                loginViewModel.validateForm(
                    etEmail.text.toString(), etPassword.text.toString()
                )
            }
            etPassword.afterTextChanged {
                loginViewModel.validateForm(
                    etEmail.text.toString(), etPassword.text.toString()
                )
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            tvGoToSignUp.paint.isUnderlineText = true
            tvGoToSignUp.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }
            btnLogin.setOnClickListener {
                loginViewModel.loginUser(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
            btnFacebook.setOnClickListener {
                loginViewModel.loginWithFacebook(this@LoginActivity)
            }
        }
    }

}