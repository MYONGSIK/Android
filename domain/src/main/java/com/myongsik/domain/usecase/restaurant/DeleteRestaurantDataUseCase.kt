package com.myongsik.domain.usecase.restaurant

import com.myongsik.domain.repository.restaurant.RestaurantRepository
import com.myongsik.domain.model.restaurant.RestaurantEntity
import javax.inject.Inject

class DeleteRestaurantDataUseCase @Inject constructor(
    private val restaurantRepository : RestaurantRepository
) {

    suspend operator fun invoke(restaurantEntity: RestaurantEntity) =
        restaurantRepository.deleteRestaurant(restaurantEntity)
}