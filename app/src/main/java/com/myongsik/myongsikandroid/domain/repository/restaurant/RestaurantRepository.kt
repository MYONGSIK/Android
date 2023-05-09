package com.myongsik.myongsikandroid.domain.repository.restaurant

import com.myongsik.myongsikandroid.domain.model.restaurant.RestaurantEntity

interface RestaurantRepository {

    suspend fun insertRestaurant(restaurantEntity: RestaurantEntity)

    suspend fun deleteRestaurant(restaurantEntity: RestaurantEntity)

    suspend fun loveIs(id : String) : RestaurantEntity
}