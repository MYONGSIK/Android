package com.myongsik.myongsikandroid.data.repository.food

import androidx.paging.PagingData
import com.myongsik.myongsikandroid.data.model.food.FoodResult
import com.myongsik.myongsikandroid.data.model.food.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.kakao.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/*
Repository 패턴을 이용한 FoodRepository interface
 */
interface FoodRepository {

    //오늘 식단, 주간식단 불러오기
    suspend fun todayGetFood() : Response<TodayFoodResponse>

    suspend fun weekGetFood() : Response<WeekFoodResponse>

    //DataStore
    //DataStore 저장
    suspend fun saveLunchEvaluation(foodResult: FoodResult, evaluation : String)

    suspend fun defaultDataStore()

    //중식평가 DataStore 불러오기
    suspend fun getLunchEvaluation() : Flow<String>

    suspend fun getLunchBEvaluation() : Flow<String>


    suspend fun getDinnerEvaluation() : Flow<String>

    //Room
    suspend fun insertFoods(restaurant: Restaurant)

    suspend fun deleteFoods(restaurant: Restaurant)

    fun getFoods() : Flow<PagingData<Restaurant>>
}