package com.myongsik.myongsikandroid.data.repository.love

import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.data.model.kakao.toInsertFoodData
import com.myongsik.myongsikandroid.domain.model.love.InsertFoodEntity
import javax.inject.Inject

class LoveRepositoryImpl @Inject constructor(
    private val loveDb : RestaurantDatabase
) : LoveRepository {

    override suspend fun insertFood(insertFoodEntity: InsertFoodEntity) {
        loveDb.restaurantDao().insertGoodFood(insertFoodEntity.toInsertFoodData())
    }
}