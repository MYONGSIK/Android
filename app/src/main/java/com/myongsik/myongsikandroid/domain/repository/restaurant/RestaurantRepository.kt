package com.myongsik.myongsikandroid.domain.repository.restaurant

import com.myongsik.myongsikandroid.domain.model.restaurant.InsertRestaurantEntity

interface RestaurantRepository {

    suspend fun insertRestaurant(insertRestaurantEntity: InsertRestaurantEntity)
}