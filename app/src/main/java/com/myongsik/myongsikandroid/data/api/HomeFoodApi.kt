package com.myongsik.myongsikandroid.data.api


import com.myongsik.myongsikandroid.data.model.food.RequestMealData
import com.myongsik.myongsikandroid.data.model.food.RequestScrap
import com.myongsik.myongsikandroid.data.model.food.ResponseMealData
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.data.model.user.ResponseUserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeFoodApi {

    @GET("/api/v2/meals/week/{area}")
    suspend fun weekGetFoodArea(
        @Path("area") area: String
    ): Response<WeekFoodResponse>

    @POST("/api/v2/reviews")
    suspend fun postReview(
        @Body body: RequestReviewData
    ): Response<ResponseReviewData>

    @POST("/api/v2/users")
    suspend fun postUser(
        @Body body: RequestUserData
    ): Response<ResponseUserData>

    @POST("/api/v2/meals/evaluate")
    suspend fun postMeal(
        @Body body: RequestMealData
    ): Response<ResponseMealData>

    @POST("/api/v2/scraps")
    suspend fun postRestaurantScrap(
        @Body body: RequestScrap
    ): Response<ResponseMealData>
}