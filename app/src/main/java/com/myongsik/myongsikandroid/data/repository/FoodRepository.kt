package com.myongsik.myongsikandroid.data.repository

import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import retrofit2.Response

interface FoodRepository {

    suspend fun todayGetFood() : Response<TodayFoodResponse>

    suspend fun weekGetFood() : Response<TodayFoodResponse>
}