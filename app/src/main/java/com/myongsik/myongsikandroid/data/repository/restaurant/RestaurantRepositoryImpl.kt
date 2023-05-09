package com.myongsik.myongsikandroid.data.repository.restaurant

import com.myongsik.myongsikandroid.data.datasource.restaurant.RestaurantDataSource
import com.myongsik.myongsikandroid.domain.model.restaurant.RestaurantEntity
import com.myongsik.myongsikandroid.domain.repository.restaurant.RestaurantRepository
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantDataSource: RestaurantDataSource
) : RestaurantRepository {

    override suspend fun insertRestaurant(restaurantEntity: RestaurantEntity) {
        restaurantDataSource.insertRestaurant(restaurantEntity)
    }

    override suspend fun deleteRestaurant(restaurantEntity: RestaurantEntity) {
        restaurantDataSource.deleteRestaurant(restaurantEntity)
    }

    override suspend fun loveIs(id: String): RestaurantEntity = restaurantDataSource.loveIs(id)

}