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
import com.myongsik.myongsikandroid.util.DateUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltWorker
class UpdateWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private var repository: FoodRepository
) :
    Worker(context, params) {

    override fun doWork(): Result {
        GlobalScope.launch {
            getMeals()
        }
        return Result.success()
    }

    private suspend fun getMeals() {
        withContext(Dispatchers.Main) {
            val currentArea = CommonUtil.getAreaName(context)
            val response = repository.dayGetFoodArea(currentArea)
            val meals = if (response.isSuccessful) {
                response.body()?.data?.map { Pair(it.mealType, it.meals) } ?: emptyList()
            } else {
                emptyList()
            }
            val remoteViews = createRemoteViews(context, meals)
            CommonUtil.updateWidget(context, remoteViews)
        }
    }

    private fun createRemoteViews(context: Context, meals: List<Pair<String?, List<String>?>>): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.item_widget_menu)
        Log.d("MenuWidget", "meals: $meals")
        remoteViews.apply {
            if (meals.isEmpty()) {
                setWeekendMessage()
                return@apply
            } else {
                setTitleData()
                setFoodTypeData(meals)
                setFoodData(meals)
            }
        }
        return remoteViews
    }

    private fun RemoteViews.setTitleData() {
        setTextViewText(R.id.tvDate, DateUtil.getCurrentDate())
        setTextViewText(R.id.tvArea, CommonUtil.getAreaName(context))
    }

    private fun RemoteViews.setWeekendMessage() {
        setViewVisibility(R.id.tvFirstFoodType, View.GONE)
        setViewVisibility(R.id.tvSecondFoodType, View.GONE)
        setViewVisibility(R.id.tvThirdFoodType, View.GONE)
        setViewVisibility(R.id.tvFirstFood, View.GONE)
        setViewVisibility(R.id.tvSecondFood, View.GONE)
        setViewVisibility(R.id.tvThirdFood, View.GONE)
        setViewVisibility(R.id.tvWeekendMessage, View.VISIBLE)
    }

    private fun RemoteViews.setFoodTypeData(meals: List<Pair<String?, List<String>?>>) {
        val foodTypeTextViewIds = listOf(R.id.tvFirstFoodType, R.id.tvSecondFoodType, R.id.tvThirdFoodType)
        meals.forEachIndexed { index, pair ->
            foodTypeTextViewIds[index].let { id ->
                if (pair.first.isNullOrBlank()) {
                    setViewVisibility(id, View.GONE)
                } else {
                    setViewVisibility(id, View.VISIBLE)
                    setTextViewText(id, pair.first)
                }
            }
        }
    }

    private fun RemoteViews.setFoodData(meals: List<Pair<String?, List<String>?>>) {
        val foodTextViewIds = listOf(R.id.tvFirstFood, R.id.tvSecondFood, R.id.tvThirdFood)
        meals.forEachIndexed { index, pair ->
            foodTextViewIds[index].let { id ->
                if (pair.second.isNullOrEmpty()) {
                    setViewVisibility(id, View.GONE)
                } else {
                    setViewVisibility(id, View.VISIBLE)
                    setTextViewText(id, pair.second?.filter { it.isNotBlank() }?.joinToString(", ") ?: "")
                }
            }
        }
    }
}