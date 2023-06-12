package com.myongsik.data.datasource.restaurant

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.myongsik.data.db.RestaurantDatabase
import com.myongsik.data.model.kakao.toRestaurantData
import com.myongsik.data.model.kakao.toRestaurantEntity
import com.myongsik.data.model.restaurant.toRequestScrapData
import com.myongsik.data.model.restaurant.toResponseScrapEntity
import com.myongsik.domain.model.restaurant.RequestScrapEntity
import com.myongsik.domain.model.restaurant.ResponseScrapEntity
import com.myongsik.domain.model.restaurant.RestaurantEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RestaurantDataSourceImpl @Inject constructor(
    private val loveDb: RestaurantDatabase,
    private val restaurantApi: com.myongsik.data.api.RestaurantApi
) : RestaurantDataSource {

    override suspend fun insertRestaurant(restaurantEntity: RestaurantEntity) {
        loveDb.restaurantDao().insertGoodFood(restaurantEntity.toRestaurantData())
    }

    override suspend fun deleteRestaurant(restaurantEntity: RestaurantEntity) {
        loveDb.restaurantDao().deleteBook(restaurantEntity.toRestaurantData())
    }

    override suspend fun loveIs(id: String): RestaurantEntity =
        loveDb.restaurantDao().loveIs(id).toRestaurantEntity()

    override suspend fun getLoveRestaurant(): Flow<PagingData<RestaurantEntity>> {
        val pagingSourceFactory = {
            loveDb.restaurantDao().getFoods()
        }

        return Pager(
            config = PagingConfig(
                pageSize = Constant.PAGING_SIZE,
                enablePlaceholders = false,
                maxSize = Constant.PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData -> pagingData.map { it.toRestaurantEntity() } }
    }

    override suspend fun getLoveListRestaurant(): Flow<List<RestaurantEntity>> {
        return loveDb.restaurantDao().getIsLoveFood()
            .map { restaurantList -> restaurantList.map { it.toRestaurantEntity() } }
    }

    override suspend fun postScrapRestaurant(requestScrapEntity: RequestScrapEntity): ResponseScrapEntity? {
        val response = restaurantApi.postRestaurantScrap(requestScrapEntity.toRequestScrapData())
        return if(response.isSuccessful) {
            response.body()?.toResponseScrapEntity()
        } else {
            null
        }
    }

}