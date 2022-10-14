package com.myongsik.myongsikandroid.data.api

import com.myongsik.myongsikandroid.data.model.SearchResponse
import com.myongsik.myongsikandroid.util.Constant.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface HomeFoodApi {

    @Headers("Authorization: KakaoAK $API_KEY")
    @GET("v3/search/book")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("sort") sort : String,
        @Query("page") page : Int,
        @Query("size") size : Int,
    ) : Response<SearchResponse>

}