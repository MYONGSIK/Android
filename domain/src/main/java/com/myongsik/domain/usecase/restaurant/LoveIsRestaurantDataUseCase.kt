package com.myongsik.myongsikandroid.domain.usecase.restaurant

import com.myongsik.myongsikandroid.domain.repository.restaurant.RestaurantRepository
import javax.inject.Inject

class LoveIsRestaurantDataUseCase @Inject constructor(
    private val restaurantRepository : RestaurantRepository
) {

    suspend operator fun invoke(id : String) = restaurantRepository.loveIs(id)
}