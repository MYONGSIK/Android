package com.myongsik.myongsikandroid.data.datasource.restaurant

import androidx.paging.PagingData
import com.myongsik.myongsikandroid.domain.model.restaurant.RequestScrapEntity
import com.myongsik.myongsikandroid.domain.model.restaurant.ResponseScrapEntity
import com.myongsik.myongsikandroid.domain.model.restaurant.RestaurantEntity
import kotlinx.coroutines.flow.Flow

interface RestaurantDataSource {

    suspend fun insertRestaurant(restaurantEntity: RestaurantEntity)

    suspend fun deleteRestaurant(restaurantEntity: RestaurantEntity)

    suspend fun loveIs(id: String): RestaurantEntity

    suspend fun getLoveRestaurant(): Flow<PagingData<RestaurantEntity>>

    suspend fun getLoveListRestaurant() : Flow<List<RestaurantEntity>>

    suspend fun postScrapRestaurant(requestScrapEntity: RequestScrapEntity): ResponseScrapEntity?
}