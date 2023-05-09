package com.myongsik.myongsikandroid.data.datasource.restaurant

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.kakao.toRestaurantData
import com.myongsik.myongsikandroid.data.model.kakao.toRestaurantEntity
import com.myongsik.myongsikandroid.domain.model.restaurant.RestaurantEntity
import com.myongsik.myongsikandroid.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RestaurantDataSourceImpl @Inject constructor(
    private val loveDb: RestaurantDatabase
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
        ).flow
            .map { pagingData -> pagingData.map { it.toRestaurantEntity() } }
    }

    override suspend fun getLoveListRestaurant(): Flow<List<RestaurantEntity>> {
        return loveDb.restaurantDao().getIsLoveFood()
            .map { restaurantList -> restaurantList.map { it.toRestaurantEntity() } }
    }

}