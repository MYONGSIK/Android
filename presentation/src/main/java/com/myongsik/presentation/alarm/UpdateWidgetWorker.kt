package com.myongsik.presentation.alarm

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.RemoteViews
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.myongsik.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.R
import com.myongsik.myongsikandroid.util.CommonUtil
import com.myongsik.myongsikandroid.util.DateUtil
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltWorker
class UpdateWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private var repository: FoodRepository
) : Worker(context, params) {

    override fun doWork(): Result {
        GlobalScope.launch {
            getMeals()
        }
        return Result.success()
    }

    private suspend fun getMeals() {
        withContext(Dispatchers.Main) {
            val currentArea = if (MyongsikApplication.prefs.getUserCampus() == "S") {
                context.getString(R.string.user_area_mcc)
            } else {
                repository.getCurrentWidgetType().firstOrNull() ?: context.getString(R.string.user_area_l)
            }

            val response = repository.dayGetFoodArea(currentArea)
            val meals = if (response.isSuccessful) {
                response.body()?.data?.map { Pair(it.mealType, it.meals) } ?: emptyList()
            } else {
                emptyList()
            }
            val remoteViews = createRemoteViews(context, meals, currentArea)
            CommonUtil.updateWidget(context, remoteViews)
        }
    }

    private fun createRemoteViews(context: Context, meals: List<Pair<String?, List<String>?>>, currentArea: String): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.item_widget_menu)
        try {
            remoteViews.apply {
                if (meals.isEmpty()) {
                    setWeekendMessage()
                    return@apply
                } else {
                    setWeekMessage()
                    setViewVisibility(R.id.thirdFoodLayout, View.GONE)
                    setTitleData(currentArea)
                    setTimeData(currentArea)
                    setFoodTypeData(meals, currentArea)
                    setFoodData(meals)
                }
            }
        } catch (e: Exception) {
            remoteViews.setWeekendMessage()
        }

        return remoteViews
    }

    private fun RemoteViews.setTimeData(currentArea: String) {
        CommonUtil.getAreaTime(context, currentArea).run {
            setTextViewText(R.id.tvFirstFoodTime, first)
            setTextViewText(R.id.tvSecondFoodTime, second)
            setTextViewText(R.id.tvThirdFoodTime, third)
        }
    }

    private fun RemoteViews.setTitleData(currentArea: String) {
        setTextViewText(R.id.tvDate, DateUtil.getCurrentDate())
        setTextViewText(R.id.tvArea, currentArea)
    }

    private fun RemoteViews.setWeekendMessage() {
        setViewVisibility(R.id.tvDate, View.GONE)
        setViewVisibility(R.id.tvArea, View.GONE)
        setViewVisibility(R.id.firstFoodLayout, View.GONE)
        setViewVisibility(R.id.secondFoodLayout, View.GONE)
        setViewVisibility(R.id.thirdFoodLayout, View.GONE)
        setViewVisibility(R.id.tvWeekendMessage, View.VISIBLE)
    }

    private fun RemoteViews.setWeekMessage() {
        setViewVisibility(R.id.tvDate, View.VISIBLE)
        setViewVisibility(R.id.tvArea, View.VISIBLE)
        setViewVisibility(R.id.tvWeekendMessage, View.GONE)
    }

    private fun RemoteViews.setFoodTypeData(meals: List<Pair<String?, List<String>?>>, currentArea: String) {
        val foodTypeTextViewIds = listOf(R.id.tvFirstFoodType, R.id.tvSecondFoodType, R.id.tvThirdFoodType)
        val foodLayoutIds = listOf(R.id.firstFoodLayout, R.id.secondFoodLayout, R.id.thirdFoodLayout)
        meals.forEachIndexed { index, pair ->
            (foodTypeTextViewIds[index] to foodLayoutIds[index]).let { ids ->
                if (pair.first.isNullOrBlank()) {
                    setViewVisibility(ids.first, View.GONE)
                    setViewVisibility(ids.second, View.GONE)
                } else {
                    setViewVisibility(ids.second, View.VISIBLE)
                    setViewVisibility(ids.first, View.VISIBLE)
                    setTextViewText(ids.first, CommonUtil.getDailyFoodParsing(context, currentArea).toList()[index])
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
                    val food = pair.second?.filter { it.isNotBlank() }?.joinToString(" ") ?: ""
                    val spannable = SpannableString(food)
                    val endPosition = pair.second?.getOrNull(0)?.length ?: 0
                    spannable.apply {
                        setSpan(ForegroundColorSpan(context.getColor(R.color.sub_color)), 0, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        setSpan(StyleSpan(Typeface.BOLD), 0, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    setTextViewText(id, spannable)
                }
            }
        }
    }
}