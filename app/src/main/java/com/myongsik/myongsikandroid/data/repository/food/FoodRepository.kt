package com.myongsik.myongsikandroid.data.repository.food

import androidx.paging.PagingData
import com.myongsik.myongsikandroid.data.model.food.FoodResult
import com.myongsik.myongsikandroid.data.model.food.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.WeekFoodAreaResponse
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/*
Repository 패턴을 이용한 FoodRepository interface
 */
interface FoodRepository {

    //오늘 식단, 주간식단 불러오기
    suspend fun todayGetFood() : Response<TodayFoodResponse>

    suspend fun weekGetFood() : Response<WeekFoodResponse>

    suspend fun weekGetFoodArea(s:String) : Response<WeekFoodResponse>


    //DataStore
    //DataStore 저장
    suspend fun saveLunchEvaluation(foodResult: FoodResult, evaluation : String)

    //하루가 지나면 DataStore 초기화
    suspend fun defaultDataStore()

    //중식평가 DataStore 불러오기
    suspend fun getLunchEvaluation() : Flow<String>

    suspend fun getLunchBEvaluation() : Flow<String>

    suspend fun getDinnerEvaluation() : Flow<String>

    //Room
    suspend fun insertFoods(restaurant: Restaurant)

    suspend fun deleteFoods(restaurant: Restaurant)

    //식당 조회 -> PagingData
    fun getFoods() : Flow<PagingData<Restaurant>>

    //들어간 장소가 현재 찜해뒀는지 판단하는 메서드
    fun loveIs(id : String) : Restaurant

    fun updateLove(id : String) : Boolean
}