package com.myongsik.myongsikandroid.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.myongsik.myongsikandroid.util.MyongsikApplication

/*
Background 작업
하루가 지나면 DataStore 를 초기화하기 위해
 */
class AlarmBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        MyongsikApplication.prefs.setString("key", "gg")
    }
}