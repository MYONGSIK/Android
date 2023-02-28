package com.myongsik.myongsikandroid.util

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val prefs : SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)
    private val editor : SharedPreferences.Editor = prefs.edit()

    fun getString(key: String, defValue: String?): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString(key, str).apply()
    }

    fun setUserCampus(input: String){
        editor.putString("MY_CAMPUS", input)
        editor.commit()
    }

    fun getUserCampus():String{
        return prefs.getString("MY_CAMPUS", "").toString()
    }

    fun setUserArea(input: String){
        editor.putString("MY_AREA", input)
        editor.commit()
    }

    fun getUserArea():String{
        return prefs.getString("MY_AREA", "").toString()
    }

    fun setUserId(input: String){
        editor.putString("MY_ID", input)
        editor.commit()
    }

    fun getUserID():String{
        return prefs.getString("MY_ID", "").toString()
    }


}