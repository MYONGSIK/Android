package com.myongsik.myongsikandroid.util

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RemoteViews
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.ui.widget.MenuWidget
import java.util.*

object CommonUtil {
    fun hideKeyboard(activity: Activity) {
        if (activity.currentFocus != null) {
            val inputManager: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun showKeyboard(editText: EditText, activity: Activity) {
        if (activity.currentFocus != null) {
            val inputManager: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputManager.showSoftInput(
                editText, InputMethodManager.SHOW_IMPLICIT
            )
        }
    }

    fun getAreaName(context: Context?): String {
        context?.run {
            return if (MyongsikApplication.prefs.getUserCampus() == "S") {
                getString(R.string.user_area_mcc)
            } else {
                when (MyongsikApplication.prefs.getUserArea()) {
                    "S" -> {
                        getString(R.string.user_area_s)
                    }
                    "L" -> {
                        getString(R.string.user_area_l)
                    }
                    "H" -> {
                        getString(R.string.user_area_h)
                    }
                    else -> {
                        getString(R.string.user_area_m)
                    }
                }
            }
        } ?: return ""
    }

    fun getDelayUntilNextMidnight(): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 5)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis > System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        return calendar.timeInMillis - System.currentTimeMillis()
    }

    fun updateWidget(context: Context, updateViews: RemoteViews) {
        val widgetProvider = ComponentName(context, MenuWidget::class.java)
        AppWidgetManager.getInstance(context).updateAppWidget(widgetProvider, updateViews)
    }
}