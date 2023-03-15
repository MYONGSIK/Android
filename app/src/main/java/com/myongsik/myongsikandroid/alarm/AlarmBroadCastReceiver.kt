package com.myongsik.myongsikandroid.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.myongsik.myongsikandroid.util.MyongsikApplication

class AlarmBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        MyongsikApplication.prefs.setString("key", "gg")
    }
}