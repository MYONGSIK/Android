package com.myongsik.myongsikandroid.data.api

import com.myongsik.myongsikandroid.util.Constant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private val okHttpClient : OkHttpClient by lazy{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private val retrofit : Retrofit by lazy{
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient) //인터셉터를 넘겨줌으로써 로그캣에서 모니터링함
            .baseUrl(BASE_URL)
            .build()
    }

    val api : HomeFoodApi by lazy{
        retrofit.create(HomeFoodApi::class.java)
    }
}