package com.myongsik.myongsikandroid.data.repository.love

import com.myongsik.myongsikandroid.domain.model.love.InsertFoodEntity

interface LoveRepository {

    suspend fun insertFood(insertFoodEntity: InsertFoodEntity)
}