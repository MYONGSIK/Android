package com.myongsik.myongsikandroid.data.api


import com.myongsik.myongsikandroid.data.model.kakao.SearchResponse
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.Constant.API_KEY
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/*
Kakao API 호출하는 interface
카카오 장소 조회 오픈 API

query : 검색어
category_group_code : 음식점, 카페
x, y : 위도, 경도 -> 명지대 위치로 설정
radius : 명지대를 기준으로 원을 그림, 1.5km 로 설정
page : 전체 페이지
size : 한 페이지당 몇 개까지 나올 것인지
sort : 정렬 -> 우리는 거리 기준으로 설정
 */
interface SearchFoodApi {

    @Headers("Authorization: KakaoAK $API_KEY")
    @GET("/v2/local/search/keyword")
    suspend fun searchFood(
        @Query("query") query: String,
        @Query("category_group_code") category_group_code: String,
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("radius") radius: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String,
    ): Response<SearchResponse>

    companion object {

        fun create(): SearchFoodApi {
            val logger = HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(Constant.KAKAO_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SearchFoodApi::class.java)
        }
    }
}