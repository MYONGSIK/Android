package com.myongsik.presentation.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import androidx.work.*
import com.myongsik.data.repository.food.FoodRepository
import com.myongsik.presentation.alarm.UpdateWidgetWorker
import dagger.hilt.android.AndroidEntryPoint
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

        val updateRequest = PeriodicWorkRequestBuilder<UpdateWidgetWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag(TAG)
            .build()

        WorkManager.getInstance(context).apply {
            enqueue(updateOnceRequest)
            enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, updateRequest)
        }
    }
}