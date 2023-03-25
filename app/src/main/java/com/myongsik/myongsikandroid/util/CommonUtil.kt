package com.myongsik.myongsikandroid.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object CommonUtil {
    fun hideKeyboard(activity: Activity) {
        if(activity.currentFocus != null) {
            val inputManager: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun showKeyboard(editText : EditText, activity: Activity) {
        if(activity.currentFocus != null) {
            val inputManager: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputManager.showSoftInput(
                editText, InputMethodManager.SHOW_IMPLICIT
            )
        }
    }
}