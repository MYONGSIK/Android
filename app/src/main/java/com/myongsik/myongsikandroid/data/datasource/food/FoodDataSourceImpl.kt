package com.myongsik.myongsikandroid.data.datasource.food

import com.myongsik.myongsikandroid.data.api.HomeFoodApi
import com.myongsik.myongsikandroid.data.model.food.toWeekFoodEntity
import com.myongsik.myongsikandroid.domain.model.food.ResponseWeekFoodEntity
import javax.inject.Inject

class FoodDataSourceImpl @Inject constructor(
    private val homeFoodApi : HomeFoodApi
) : FoodDataSource {

    override suspend fun weekGetFoodArea(s: String): ResponseWeekFoodEntity? {
        val response = homeFoodApi.weekGetFoodArea(s)
        return if(response.isSuccessful) {
            response.body()?.toWeekFoodEntity()
        } else {
            null
        }
    }
}