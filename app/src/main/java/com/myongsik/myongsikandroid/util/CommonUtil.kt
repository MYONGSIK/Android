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

    fun getAreaTime(context: Context?): Triple<String?, String?, String?> {
        context?.run {
            return if (MyongsikApplication.prefs.getUserCampus() == "S") {
                Triple(
                    getString(R.string.home_launch_time),
                    getString(R.string.home_launch_time),
                    getString(R.string.home_dinner_time)
                )
            } else {
                when (MyongsikApplication.prefs.getUserArea()) {
                    "S" -> {
                        Triple(
                            getString(R.string.select_teacher_launch_time),
                            getString(R.string.select_teacher_dinner_time),
                            null
                        )
                    }
                    "L" -> {
                        Triple(
                            getString(R.string.select_room_launch_time),
                            getString(R.string.select_room_dinner_time),
                            null
                        )
                    }
                    "H" -> {
                        Triple(
                            getString(R.string.select_student_breakfast_time),
                            getString(R.string.select_student_breakfast_time),
                            getString(R.string.select_student_launch_time)
                        )
                    }
                    else -> {
                        Triple(
                            getString(R.string.select_new_rice_time),
                            getString(R.string.select_new_salad_time),
                            getString(R.string.select_new_salad_time)
                        )
                    }
                }
            }
        } ?: return Triple(null, null, null)

    }

    fun updateWidget(context: Context, updateViews: RemoteViews) {
        val widgetProvider = ComponentName(context, MenuWidget::class.java)
        AppWidgetManager.getInstance(context).updateAppWidget(widgetProvider, updateViews)
    }
}