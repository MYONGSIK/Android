package com.myongsik.domain.usecase.restaurant

import com.myongsik.domain.repository.restaurant.RestaurantRepository
import javax.inject.Inject

class GetListRestaurantDataUseCase @Inject constructor(
    private val restaurantRepository : RestaurantRepository
) {

    suspend operator fun invoke() = restaurantRepository.getLoveListRestaurant()
}