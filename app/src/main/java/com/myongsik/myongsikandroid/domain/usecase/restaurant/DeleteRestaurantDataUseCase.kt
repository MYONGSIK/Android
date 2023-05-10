package com.myongsik.myongsikandroid.domain.usecase.restaurant

import com.myongsik.myongsikandroid.domain.repository.restaurant.RestaurantRepository
import com.myongsik.myongsikandroid.domain.model.restaurant.RestaurantEntity
import javax.inject.Inject

class DeleteRestaurantDataUseCase @Inject constructor(
    private val restaurantRepository : RestaurantRepository
) {

    suspend operator fun invoke(restaurantEntity: RestaurantEntity) =
        restaurantRepository.deleteRestaurant(restaurantEntity)
}