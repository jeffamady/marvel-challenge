package com.amadydev.intermedia.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.amadydev.intermedia.R
import com.amadydev.intermedia.ui.login.LoginActivity
import com.amadydev.intermedia.ui.main.MainScreenActivity
import com.amadydev.intermedia.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel.isUserLoggedIn()

        Handler(Looper.getMainLooper()).postDelayed({
            splashViewModel.splashState.observe(this) {
                when (it) {
                    SplashViewModel.SplashState.Logged ->
                        startActivity(Intent(this, MainScreenActivity::class.java))
                    SplashViewModel.SplashState.NotLogged ->
                        startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }, 2500)
    }
}