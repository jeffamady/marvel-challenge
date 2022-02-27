package com.amadydev.intermedia.ui.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.amadydev.intermedia.R
import com.amadydev.intermedia.databinding.ActivityMainScreenBinding
import com.amadydev.intermedia.databinding.ActivitySignUpBinding
import com.amadydev.intermedia.ui.base.BaseActivity
import com.amadydev.intermedia.ui.login.LoginActivity
import com.amadydev.intermedia.ui.main.MainScreenActivity
import com.amadydev.intermedia.utils.extensions.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validateData()
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        signUpViewModel.signUpState.observe(this) {
            with(binding) {
                when (it) {
                    is SignUpViewModel.SignUpState.NameError ->
                        etName.error = getString(it.resourceId)
                    is SignUpViewModel.SignUpState.EmailError ->
                        etEmail.error = getString(it.resourceId)
                    is SignUpViewModel.SignUpState.PasswordError ->
                        etPassword.error = getString(it.resourceId)
                    is SignUpViewModel.SignUpState.IsFormValid -> {
                        btnSignUp.isEnabled = it.isFormValid
                        if (it.isFormValid)
                            btnSignUp.setTextColor(Color.WHITE)
                        else
                            btnSignUp.setTextColor(Color.BLACK)
                    }
                    is SignUpViewModel.SignUpState.Loading ->
                        showProgressDialog(it.isLoading)
                    is SignUpViewModel.SignUpState.Success -> {
                        Toast.makeText(
                            this@SignUpActivity,
                            getString(it.resourceId),
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@SignUpActivity, MainScreenActivity::class.java))
                        finish()
                    }
                    SignUpViewModel.SignUpState.Error ->
                        showErrorSnackBar(root, getString(R.string.signup_form_error))
                }
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            tvGoToLogin.paint.isUnderlineText = true
            tvGoToLogin.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            }
            btnSignUp.setOnClickListener {
                signUpViewModel.registerUser(
                    binding.etName.text.toString(),
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }
    }


    private fun validateData() {
        with(binding) {
            etName.afterTextChanged {
                signUpViewModel.validateForm(
                    etName.text.toString(), etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
            etEmail.afterTextChanged {
                signUpViewModel.validateForm(
                    etName.text.toString(),
                    etEmail.text.toString(), etPassword.text.toString()
                )
            }
            etPassword.afterTextChanged {
                signUpViewModel.validateForm(
                    etName.text.toString(),
                    etEmail.text.toString(), etPassword.text.toString()
                )
            }
        }
    }

}