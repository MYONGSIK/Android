package com.myongsik.myongsikandroid.data.datasource.restaurant

import com.myongsik.myongsikandroid.domain.model.restaurant.InsertRestaurantEntity

interface RestaurantDataSource {

    suspend fun insertRestaurant(insertRestaurantEntity: InsertRestaurantEntity)
}