package com.myongsik.myongsikandroid.data.datasource.restaurant

import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.data.model.kakao.toRestaurantData
import com.myongsik.myongsikandroid.data.model.kakao.toRestaurantEntity
import com.myongsik.myongsikandroid.domain.model.restaurant.RestaurantEntity
import javax.inject.Inject

class RestaurantDataSourceImpl  @Inject constructor(
    private val loveDb : RestaurantDatabase
) : RestaurantDataSource {

    override suspend fun insertRestaurant(restaurantEntity: RestaurantEntity) {
        loveDb.restaurantDao().insertGoodFood(restaurantEntity.toRestaurantData())
    }

    override suspend fun deleteRestaurant(restaurantEntity: RestaurantEntity) {
        loveDb.restaurantDao().deleteBook(restaurantEntity.toRestaurantData())
    }

    override suspend fun loveIs(id: String): RestaurantEntity = loveDb.restaurantDao().loveIs(id).toRestaurantEntity()

}