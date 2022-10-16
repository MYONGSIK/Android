package com.myongsik.myongsikandroid.data.api


import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeFoodApi {

    @GET("/api/v1/foods")
    suspend fun todayGetFood() : Response<TodayFoodResponse>

    @GET("/api/v1/foods/week")
    suspend fun weekGetFood() : Response<TodayFoodResponse>

}