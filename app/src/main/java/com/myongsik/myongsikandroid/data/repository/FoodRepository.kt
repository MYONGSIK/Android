package com.myongsik.myongsikandroid.data.repository

import com.myongsik.myongsikandroid.data.model.FoodResult
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.WeekFoodResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FoodRepository {

    suspend fun todayGetFood() : Response<TodayFoodResponse>

    suspend fun weekGetFood() : Response<WeekFoodResponse>

    //DataStore
    //중식평가 DataStore 저장
    suspend fun saveLunchEvaluation(evaluation : String)

    //중식평가 DataStore 불러오기
    suspend fun getLunchEvaluation() : Flow<String>

    //석식평가 DataStore 저장
    suspend fun saveDinnerEvaluation(evaluation : String)

    suspend fun getDinnerEvaluation() : Flow<String>


}