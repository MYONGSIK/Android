package com.myongsik.myongsikandroid.domain.repository.food

import com.myongsik.myongsikandroid.domain.model.food.ResponseWeekFoodEntity

interface FoodV2Repository {
    suspend fun weekGetFoodArea(s: String): ResponseWeekFoodEntity?
}