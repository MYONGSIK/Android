package com.myongsik.myongsikandroid.data.api


import com.myongsik.myongsikandroid.data.model.food.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.kakao.SearchResponse
import com.myongsik.myongsikandroid.util.Constant.API_KEY
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.logging.Level

/*
Kakao API 호출하는 interface
 */
interface SearchFoodApi {

    @Headers("Authorization: KakaoAK $API_KEY")
    @GET("v2/local/search/keyword")
    suspend fun searchFood(
        @Query("query") query : String,
        @Query("category_group_code") category_group_code : String,
        @Query("x") x : String,
        @Query("y") y : String,
        @Query("radius") radius : Int,
        @Query("page") page : Int,
        @Query("size") size : Int,
    ) : Response<SearchResponse>

    companion object{
        private const val BASE_URL = "https://dapi.kakao.com"

        fun create() : SearchFoodApi {
            val logger = HttpLoggingInterceptor().apply { level =
                HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SearchFoodApi::class.java)

        }
    }

}