package com.myongsik.myongsikandroid.data.repository


import com.myongsik.myongsikandroid.data.api.RetrofitInstance.api
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import retrofit2.Response
import retrofit2.Retrofit

class FoodRepositoryImpl(

) : FoodRepository {

    override suspend fun todayGetFood(): Response<TodayFoodResponse> {
        return api.todayGetFood()
    }

    override suspend fun weekGetFood(): Response<TodayFoodResponse> {
        return api.weekGetFood()
    }
}