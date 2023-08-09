package com.myongsik.myongsikandroid.data.repository.food

import androidx.datastore.preferences.core.Preferences
import com.myongsik.myongsikandroid.data.model.food.DayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.RankRestaurantResponse
import com.myongsik.myongsikandroid.data.model.food.ResponseOneRestaurant
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FoodRepository {

    //API
    suspend fun dayGetFoodArea(area: String): Response<DayFoodResponse>

    suspend fun getRankRestaurant(sort : String, campus : String, size : Int) : Response<RankRestaurantResponse>

    suspend fun getOneRestaurant(storeId : Int) : Response<ResponseOneRestaurant>

    //DataStore
    suspend fun saveSortType(key: Preferences.Key<String>, value: String)

    suspend fun saveWidgetType(type : String)

    suspend fun getCurrentSortType(): Flow<String>

    suspend fun getCurrentWidgetType(): Flow<String?>
}