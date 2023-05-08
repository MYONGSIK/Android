package com.myongsik.myongsikandroid.data.datasource.restaurant

import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.data.model.kakao.toInsertFoodData
import com.myongsik.myongsikandroid.domain.model.restaurant.InsertRestaurantEntity
import javax.inject.Inject

class RestaurantDataSourceImpl  @Inject constructor(
    private val loveDb : RestaurantDatabase
) : RestaurantDataSource {

    override suspend fun insertRestaurant(insertRestaurantEntity: InsertRestaurantEntity) {
        loveDb.restaurantDao().insertGoodFood(insertRestaurantEntity.toInsertFoodData())
    }
}