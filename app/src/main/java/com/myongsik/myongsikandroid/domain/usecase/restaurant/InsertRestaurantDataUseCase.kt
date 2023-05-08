package com.myongsik.myongsikandroid.domain.usecase.restaurant

import com.myongsik.myongsikandroid.data.repository.restaurant.RestaurantRepository
import com.myongsik.myongsikandroid.domain.model.restaurant.InsertRestaurantEntity
import javax.inject.Inject

class InsertRestaurantDataUseCase @Inject constructor(
    private val restaurantRepository : RestaurantRepository
) : LoveCase {

    suspend operator fun invoke(insertRestaurantEntity: InsertRestaurantEntity) =
        restaurantRepository.insertRestaurant(insertRestaurantEntity)
}