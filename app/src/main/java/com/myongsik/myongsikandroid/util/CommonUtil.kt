package com.myongsik.myongsikandroid.util

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RemoteViews
import android.widget.Toast
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.alarm.UpdateWidgetWorker
import com.myongsik.myongsikandroid.data.type.WidgetType
import com.myongsik.myongsikandroid.data.type.toWidgetType
import com.myongsik.myongsikandroid.ui.widget.MenuWidget

object CommonUtil {
    fun hideKeyboard(activity: Activity) {
        if (activity.currentFocus != null) {
            val inputManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun showKeyboard(editText: EditText, activity: Activity) {
        if (activity.currentFocus != null) {
            val inputManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputManager.showSoftInput(
                editText, InputMethodManager.SHOW_IMPLICIT
            )
        }
    }

    fun distanceMapper(distance: String): String {
        return if (distance.length >= 4) {
            "${distance[0]}.${distance[1]}km"
        } else {
            "${distance}m"
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

    fun getAreaTime(context: Context?, currentArea: String): Triple<String?, String?, String?> {
        context?.run {
            return if (MyongsikApplication.prefs.getUserCampus() == "S") {
                Triple(
                    getString(R.string.home_launch_time), getString(R.string.home_launch_time), getString(R.string.home_dinner_time)
                )
            } else {
                when (currentArea.toWidgetType()) {
                    WidgetType.TEACHER -> {
                        Triple(
                            getString(R.string.select_teacher_launch_time), getString(R.string.select_teacher_dinner_time), null
                        )
                    }
                    WidgetType.STUDENT -> {
                        Triple(
                            getString(R.string.select_student_breakfast_time), getString(R.string.select_student_launch_time), null
                        )
                    }
                    WidgetType.MYONGJIN -> {
                        Triple(
                            getString(R.string.select_new_rice_time), getString(R.string.select_new_salad_time), getString(R.string.select_new_salad_time)
                        )
                    }
                    else -> {
                        Triple(
                            getString(R.string.select_room_launch_time), getString(R.string.select_room_dinner_time), null
                        )
                    }
                }
            }
        } ?: return Triple(null, null, null)
    }

    fun getDailyFoodParsing(context: Context?, currentArea: String): Triple<String?, String?, String?> {
        context?.run {
            return if (MyongsikApplication.prefs.getUserCampus() == "S") {
                Triple(
                    getString(R.string.lunch_a_text), getString(R.string.lunch_b_text), getString(R.string.dinner_text)
                )
            } else {
                when (currentArea.toWidgetType()) {
                    WidgetType.STUDENT -> {
                        Triple(
                            getString(R.string.break_fast), getString(R.string.lunch_text), null
                        )
                    }
                    WidgetType.MYONGJIN -> {
                        Triple(
                            getString(R.string.rice), getString(R.string.salad), getString(R.string.fried_rice)
                        )
                    }
                    else -> {
                        Triple(
                            getString(R.string.lunch_text), getString(R.string.dinner_text), null
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

    fun batteryExceptionDialog(context: Context?) {
        if (context != null) {
            val applicationContext = context.applicationContext
            val packageName = applicationContext.packageName
            val pm = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                Intent().apply {
                    action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                    data = Uri.parse("package:$packageName")
                }.let(context::startActivity)

                val updateOnceRequest = OneTimeWorkRequestBuilder<UpdateWidgetWorker>().setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                ).build()

                WorkManager.getInstance(context).apply {
                    enqueue(updateOnceRequest)
                }
            } else {
                Toast.makeText(context, context.getString(R.string.battery_optimize_early), Toast.LENGTH_SHORT).show()
            }
        }
    }
}