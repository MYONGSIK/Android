package com.myongsik.myongsikandroid.data.api


import com.myongsik.myongsikandroid.data.model.food.*
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.data.model.user.ResponseUserData
import retrofit2.Response
import retrofit2.http.*

interface HomeFoodApi {

    @GET("/api/v2/meals/week/{area}")
    suspend fun weekGetFoodArea(
        @Path("area") area: String
    ): Response<WeekFoodResponse>

    @GET("/api/v2/meals/{area}")
    suspend fun dayGetFoodArea(
        @Path("area") area: String
    ): Response<DayFoodResponse>

    @POST("/api/v2/reviews/area")
    suspend fun postReview(
        @Body body: RequestReviewData
    ): Response<ResponseReviewData>

    @POST("/api/v2/meals/evaluate")
    suspend fun postMeal(
        @Body body: RequestMealData
    ): Response<ResponseMealData>

    @POST("/api/v2/scraps")
    suspend fun postRestaurantScrap(
        @Body body: RequestScrap
    ): Response<ResponseScrap>

    @GET("/api/v2/scraps/store")
    suspend fun getRankRestaurant(
        @Query("sort") sort : String = "scrapCount,desc",
        @Query("campus") campus: String,
        @Query("size") size : Int = 20
    ) : Response<RankRestaurantResponse>

    @GET("/api/v2/scraps/store/{storeId}")
    suspend fun getOneRestaurant(
        @Path("storeId") storeId : Int
    ) : Response<ResponseOneRestaurant>
}