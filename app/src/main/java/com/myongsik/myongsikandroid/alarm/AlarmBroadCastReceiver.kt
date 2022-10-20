package com.myongsik.myongsikandroid.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.myongsik.myongsikandroid.ui.view.MainActivity
import com.myongsik.myongsikandroid.ui.viewmodel.MainViewModel
import com.myongsik.myongsikandroid.util.MyongsikApplication

class AlarmBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        MyongsikApplication.prefs.setString("key", "gg")
    }
}