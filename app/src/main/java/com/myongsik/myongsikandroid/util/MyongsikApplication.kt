package com.myongsik.myongsikandroid.util

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import net.daum.mf.map.api.MapView
import javax.inject.Inject

@HiltAndroidApp
class MyongsikApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workFactory: HiltWorkerFactory
    companion object{
        var appContext : Context? = null

        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workFactory)
            .build()
}