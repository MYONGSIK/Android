package com.myongsik.myongsikandroid.data.repository.restaurant

import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.data.model.kakao.toInsertFoodData
import com.myongsik.myongsikandroid.domain.model.restaurant.InsertRestaurantEntity
import com.myongsik.myongsikandroid.domain.repository.restaurant.RestaurantRepository
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val loveDb : RestaurantDatabase
) : RestaurantRepository {

    override suspend fun insertRestaurant(insertRestaurantEntity: InsertRestaurantEntity) {
        loveDb.restaurantDao().insertGoodFood(insertRestaurantEntity.toInsertFoodData())
    }
}