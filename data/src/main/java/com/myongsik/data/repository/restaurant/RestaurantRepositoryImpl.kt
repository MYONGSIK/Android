package com.myongsik.data.repository.restaurant

import androidx.paging.PagingData
import com.myongsik.data.datasource.restaurant.RestaurantDataSource
import com.myongsik.domain.model.restaurant.RequestScrapEntity
import com.myongsik.domain.model.restaurant.ResponseScrapEntity
import com.myongsik.domain.model.restaurant.RestaurantEntity
import com.myongsik.domain.repository.restaurant.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantDataSource: RestaurantDataSource
) : RestaurantRepository {

    override suspend fun insertRestaurant(restaurantEntity: RestaurantEntity) {
        restaurantDataSource.insertRestaurant(restaurantEntity)
    }

    override suspend fun deleteRestaurant(restaurantEntity: RestaurantEntity) {
        restaurantDataSource.deleteRestaurant(restaurantEntity)
    }

    override suspend fun loveIs(id: String): RestaurantEntity = restaurantDataSource.loveIs(id)

    override suspend fun getLoveRestaurant(): Flow<PagingData<RestaurantEntity>>
        = restaurantDataSource.getLoveRestaurant()

    override suspend fun getLoveListRestaurant(): Flow<List<RestaurantEntity>>
        = restaurantDataSource.getLoveListRestaurant()

    override suspend fun postRestaurantScrap(requestScrapEntity: RequestScrapEntity): ResponseScrapEntity? {
        return restaurantDataSource.postScrapRestaurant(requestScrapEntity)
    }

}