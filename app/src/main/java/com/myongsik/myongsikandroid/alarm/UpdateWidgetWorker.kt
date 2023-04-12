package com.myongsik.myongsikandroid.alarm

import android.content.Context
import android.util.Log
import android.view.View
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
            val currentArea = CommonUtil.getAreaName(context)
            val response = repository.dayGetFoodArea(currentArea)
            val meals = if (response.isSuccessful) {
                response.body()?.data?.map { Pair(it.mealType, it.meals) } ?: emptyList()
            } else {
                emptyList()
            }

            val remoteViews = createRemoteViews(context, currentArea, meals)
            CommonUtil.updateWidget(context, remoteViews)
        }
    }

    private fun createRemoteViews(context: Context, currentArea: String, meals: List<Pair<String?, List<String>?>>): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.item_widget_menu)
        Log.d("MenuWidget", "meals: $meals")
        remoteViews.apply {
            setTextViewText(R.id.tvArea, currentArea)
            setFoodTypeData(meals)
            setFoodData(meals)
        }
        return remoteViews
    }

    private fun RemoteViews.setFoodTypeData(meals: List<Pair<String?, List<String>?>>) {
        val foodTypeTextViewIds = listOf(R.id.tvFirstFoodType, R.id.tvSecondFoodType, R.id.tvThirdFoodType)
        foodTypeTextViewIds.forEachIndexed { index, id ->
            val pair = meals.getOrNull(index)
            if (pair?.first.isNullOrBlank()) {
                setViewVisibility(id, View.GONE)
            } else {
                setViewVisibility(id, View.VISIBLE)
                setTextViewText(id, pair?.first)
            }
        }
    }

    private fun RemoteViews.setFoodData(meals: List<Pair<String?, List<String>?>>) {
        val foodTextViewIds = listOf(R.id.tvFirstFood, R.id.tvSecondFood, R.id.tvThirdFood)
        foodTextViewIds.forEachIndexed { index, id ->
            val pair = meals.getOrNull(index)
            if (pair?.second.isNullOrEmpty()) {
                setViewVisibility(id, View.GONE)
            } else {
                setViewVisibility(id, View.VISIBLE)
                setTextViewText(id, pair?.second?.filter { it.isNotBlank() }?.joinToString(", ") ?: "")
            }
        }
    }
}