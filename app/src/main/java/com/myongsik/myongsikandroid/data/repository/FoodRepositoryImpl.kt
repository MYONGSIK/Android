package com.myongsik.myongsikandroid.data.repository


import com.myongsik.myongsikandroid.data.api.RetrofitInstance.api
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import retrofit2.Response

class FoodRepositoryImpl(

) : FoodRepository {

    //오늘 식단 조회
    override suspend fun todayGetFood(): Response<TodayFoodResponse> {
        return api.todayGetFood()
    }

    //주간 식단 조회
    override suspend fun weekGetFood(): Response<TodayFoodResponse> {
        return api.weekGetFood()
    }
}