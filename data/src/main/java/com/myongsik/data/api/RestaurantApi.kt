package com.myongsik.data.api

import com.myongsik.myongsikandroid.data.model.restaurant.RequestScrap
import com.myongsik.myongsikandroid.data.model.restaurant.ResponseScrap
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RestaurantApi {

    @POST("/api/v2/scraps")
    suspend fun postRestaurantScrap(
        @Body body: RequestScrap
    ): Response<ResponseScrap>
}