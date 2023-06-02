package com.myongsik.domain.usecase.restaurant

import com.myongsik.domain.model.restaurant.RequestScrapEntity
import com.myongsik.domain.repository.restaurant.RestaurantRepository
import javax.inject.Inject

class PostScrapRestaurantDataUseCase @Inject constructor(
    private val restaurantRepository : RestaurantRepository
) {

    suspend operator fun invoke(requestScrapEntity: RequestScrapEntity)
        = restaurantRepository.postRestaurantScrap(requestScrapEntity)
}