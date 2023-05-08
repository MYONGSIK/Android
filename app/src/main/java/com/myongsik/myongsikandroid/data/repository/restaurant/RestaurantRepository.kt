package com.myongsik.myongsikandroid.data.repository.restaurant

import com.myongsik.myongsikandroid.domain.model.restaurant.InsertRestaurantEntity

interface RestaurantRepository {

    suspend fun insertRestaurant(insertRestaurantEntity: InsertRestaurantEntity)
}