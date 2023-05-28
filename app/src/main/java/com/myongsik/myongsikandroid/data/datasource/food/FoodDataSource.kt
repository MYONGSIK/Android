package com.myongsik.myongsikandroid.data.datasource.food

import com.myongsik.myongsikandroid.domain.model.food.ResponseWeekFoodEntity

interface FoodDataSource {

    suspend fun weekGetFoodArea(s: String): ResponseWeekFoodEntity?

}