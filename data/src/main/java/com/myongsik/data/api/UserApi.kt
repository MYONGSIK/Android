package com.myongsik.data.api

import com.myongsik.data.model.user.RequestUserData
import com.myongsik.data.model.user.ResponseUserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/api/v2/users")
    suspend fun postUser(@Body body: RequestUserData): Response<ResponseUserData>
}