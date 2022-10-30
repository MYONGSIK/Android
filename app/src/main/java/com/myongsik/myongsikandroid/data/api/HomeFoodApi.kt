package com.myongsik.myongsikandroid.data.api


import com.myongsik.myongsikandroid.data.model.food.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.kakao.SearchResponse
import com.myongsik.myongsikandroid.util.Constant.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/*
API 호출하는 interface
 */
interface HomeFoodApi {

    @GET("/api/v1/foods")
    suspend fun todayGetFood() : Response<TodayFoodResponse>

    @GET("/api/v1/foods/week")
    suspend fun weekGetFood() : Response<WeekFoodResponse>

//    @Headers("Authorization: KakaoAK $API_KEY")
//    @GET("v2/local/search/keyword")
//    suspend fun searchFood(
//        @Query("query") query : String,
//        @Query("category_group_code") category_group_code : String,
//        @Query("x") x : String,
//        @Query("y") y : String,
//        @Query("radius") radius : Int,
//        @Query("page") page : Int,
//        @Query("size") size : Int,
//    ) : Response<SearchResponse>

}