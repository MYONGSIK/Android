package com.myongsik.myongsikandroid.data.datasource.restaurant

import com.myongsik.myongsikandroid.domain.model.restaurant.RestaurantEntity

interface RestaurantDataSource {

    suspend fun insertRestaurant(restaurantEntity: RestaurantEntity)

    suspend fun deleteRestaurant(restaurantEntity: RestaurantEntity)

    suspend fun loveIs(id: String): RestaurantEntity
}