package com.myongsik.myongsikandroid.data.repository.food

import androidx.datastore.preferences.core.Preferences
import com.myongsik.myongsikandroid.base.ApiResponse
import com.myongsik.myongsikandroid.data.model.food.DayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.RankRestaurantResponse
import com.myongsik.myongsikandroid.data.model.food.ResponseOneRestaurant
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FoodRepository {

    //API
    suspend fun dayGetFoodArea(area: String): Response<DayFoodResponse>

    suspend fun postReview(requestReviewData: RequestReviewData): Response<ResponseReviewData>

    suspend fun getRankRestaurant(sort : String, campus : String, size : Int) : Response<RankRestaurantResponse>

    suspend fun getOneRestaurant(storeId : Int) : Response<ResponseOneRestaurant>

    //DataStore
    suspend fun saveSortType(key: Preferences.Key<String>, value: String)

    suspend fun saveWidgetType(type : String)

    suspend fun getCurrentSortType(): Flow<String>

    suspend fun getCurrentWidgetType(): Flow<String?>
}