package com.myongsik.myongsikandroid.domain.usecase.restaurant

import com.myongsik.myongsikandroid.domain.model.restaurant.RequestScrapEntity
import com.myongsik.myongsikandroid.domain.repository.restaurant.RestaurantRepository
import javax.inject.Inject

class PostScrapRestaurantDataUseCase @Inject constructor(
    private val restaurantRepository : RestaurantRepository
) {

    suspend operator fun invoke(requestScrapEntity: RequestScrapEntity)
        = restaurantRepository.postRestaurantScrap(requestScrapEntity)
}