package com.myongsik.myongsikandroid.data.repository

import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.WeekFoodResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FoodRepository {

    suspend fun todayGetFood() : Response<TodayFoodResponse>

    suspend fun weekGetFood() : Response<WeekFoodResponse>

    //DataStore
    suspend fun saveEvaluation(evaluation : String)

    suspend fun getEvaluation() : Flow<String>
}