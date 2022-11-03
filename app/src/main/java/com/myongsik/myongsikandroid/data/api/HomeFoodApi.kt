package com.myongsik.myongsikandroid.data.api


import com.myongsik.myongsikandroid.data.model.food.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import retrofit2.Response
import retrofit2.http.GET

/*
음식 API 호출하는 interface
todayGetFood : 오늘 음식 조회
weekGetFood : 주간 음식 조회
 */
interface HomeFoodApi {

    @GET("/api/v1/foods")
    suspend fun todayGetFood() : Response<TodayFoodResponse>

    @GET("/api/v1/foods/week")
    suspend fun weekGetFood() : Response<WeekFoodResponse>

}