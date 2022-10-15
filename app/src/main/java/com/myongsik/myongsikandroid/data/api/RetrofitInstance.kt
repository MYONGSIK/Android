package com.myongsik.myongsikandroid.data.api

import com.myongsik.myongsikandroid.util.Constant.MYONG_SIK_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(MYONG_SIK_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    val api : HomeFoodApi by lazy{
        retrofit.create(HomeFoodApi::class.java)
    }
}