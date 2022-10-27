package com.myongsik.myongsikandroid.data.api


import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.WeekFoodResponse
import retrofit2.Response
import retrofit2.http.GET

/*
API 호출하는 interface
 */
interface HomeFoodApi {

    @GET("/api/v1/foods")
    suspend fun todayGetFood() : Response<TodayFoodResponse>

    @GET("/api/v1/foods/week")
    suspend fun weekGetFood() : Response<WeekFoodResponse>

}