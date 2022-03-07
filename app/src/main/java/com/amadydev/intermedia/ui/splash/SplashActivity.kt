package com.amadydev.intermedia.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.amadydev.intermedia.R
import com.amadydev.intermedia.ui.login.LoginActivity
import com.amadydev.intermedia.ui.main.MainScreenActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
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