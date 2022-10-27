package com.myongsik.myongsikandroid.util

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyongsikApplication : Application(){
    companion object{
        var appContext : Context? = null

        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}