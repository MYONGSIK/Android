package com.myongsik.myongsikandroid.alarm

import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.util.CommonUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@HiltWorker
class UpdateWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private var repository: FoodRepository
) :
    Worker(context, params) {

    override fun doWork(): Result {
        getMeals()
        return Result.success()
    }

    private fun getMeals() {
        GlobalScope.launch {
            val response = repository.dayGetFoodArea(CommonUtil.getAreaName(context))
            val meals = if (response.isSuccessful) {
                response.body()?.data?.map { it.meals } ?: emptyList()
            } else {
                emptyList()
            }

            val remoteViews = createRemoteViews(context, meals)
            CommonUtil.updateWidget(context, remoteViews)
        }
    }

    private fun createRemoteViews(context: Context, meals: List<List<String>>): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.item_widget_menu)
        remoteViews.apply {
            Log.d("MenuWidget", "meals: $meals")
            setTextViewText(R.id.tvArea, CommonUtil.getAreaName(context))
            setTextViewText(R.id.tvFirstFood, meals[0].filter { it.isNotBlank() }.joinToString(", "))
            setTextViewText(R.id.tvSecondFood, meals[1].filter { it.isNotBlank() }.joinToString(", "))
            setTextViewText(R.id.tvThirdFood, meals[2].filter { it.isNotBlank() }.joinToString(", "))
        }
        return remoteViews
    }
}