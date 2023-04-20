package com.myongsik.myongsikandroid.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import androidx.room.Update
import androidx.work.*
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.alarm.UpdateWidgetWorker
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.util.CommonUtil
import com.myongsik.myongsikandroid.util.DateUtil
import com.myongsik.myongsikandroid.util.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MenuWidget : AppWidgetProvider() {

    companion object {
        const val TAG = "MenuWidget"
    }

    @Inject
    lateinit var repository: FoodRepository

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        if (context == null || !NetworkUtils.getNetworkConnected(context)) {
            return
        }
        Log.d(TAG, "onUpdate")
        startWidgetUpdateWorker(context)
    }

    private fun startWidgetUpdateWorker(context: Context) {
        val updateOnceRequest = OneTimeWorkRequestBuilder<UpdateWidgetWorker>()
            .setConstraints(constraints)
            .build()

        val updateRequest = PeriodicWorkRequestBuilder<UpdateWidgetWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(DateUtil.getDelayUntilNextMidnight(), TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .addTag(TAG)
            .build()

        WorkManager.getInstance(context).apply {
            enqueue(updateOnceRequest)
            enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, updateRequest)
        }
    }
}