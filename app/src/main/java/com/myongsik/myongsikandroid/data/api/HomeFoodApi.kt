package com.myongsik.myongsikandroid.data.api


import com.myongsik.myongsikandroid.data.model.food.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.WeekFoodAreaResponse
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

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

    @GET("/api/v2/meals/week/{area}")
    suspend fun weekGetFoodArea(
        @Path("area") area: String
    ) : Response<WeekFoodResponse>

}