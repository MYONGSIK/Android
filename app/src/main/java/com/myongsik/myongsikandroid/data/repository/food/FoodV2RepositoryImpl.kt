package com.myongsik.myongsikandroid.data.repository.food

import com.myongsik.myongsikandroid.data.datasource.food.FoodDataSource
import com.myongsik.myongsikandroid.domain.model.food.ResponseWeekFoodEntity
import com.myongsik.myongsikandroid.domain.repository.food.FoodV2Repository
import javax.inject.Inject

class FoodV2RepositoryImpl @Inject constructor(
    private val foodDataSource: FoodDataSource
) : FoodV2Repository {

    override suspend fun weekGetFoodArea(s: String): ResponseWeekFoodEntity? {
        return foodDataSource.weekGetFoodArea(s)
    }
}