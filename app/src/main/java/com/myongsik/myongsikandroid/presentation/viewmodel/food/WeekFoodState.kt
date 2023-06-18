package com.myongsik.myongsikandroid.presentation.viewmodel.food

import com.myongsik.myongsikandroid.domain.model.food.ResponseWeekFoodEntity

sealed class WeekFoodState {

    object UnInitialized : WeekFoodState()

    object Loading : WeekFoodState()

    data class SuccessWeekFoodGetData(val getWeekFoodData: ResponseWeekFoodEntity) : WeekFoodState()

    data class Error(val errorCode: Int) : WeekFoodState()
}
