package com.amadydev.intermedia

import android.app.Application
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.amadydev.intermedia.ui.login.LoginActivity
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MarvelApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        crashConfig()
    }


    private fun crashConfig() {
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
            .showErrorDetails(false)
            .minTimeBetweenCrashesMs(2000)
            .logErrorOnRestart(false)
            .errorDrawable(R.drawable.ic_superhero)
            .restartActivity(LoginActivity::class.java)
            .apply()
    }
}