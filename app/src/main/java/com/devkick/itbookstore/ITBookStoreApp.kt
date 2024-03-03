package com.devkick.itbookstore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ITBookStoreApp : Application() {
    override fun onCreate() {
        super.onCreate()

        settingTimber()
    }

    private fun settingTimber() {
        Timber.plant(Timber.DebugTree())
    }
}