package com.myongsik.myongsikandroid.data.repository.food

import androidx.datastore.preferences.core.Preferences
import androidx.paging.PagingData
import com.myongsik.myongsikandroid.data.model.food.*
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.data.model.user.ResponseUserData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FoodRepository {

    //API
    suspend fun weekGetFoodArea(s: String): Response<WeekFoodResponse>

    suspend fun dayGetFoodArea(area: String): Response<DayFoodResponse>

    suspend fun postReview(requestReviewData: RequestReviewData): Response<ResponseReviewData>

    suspend fun postScrapRestaurant(requestScrap: RequestScrap): Response<ResponseScrap>

    suspend fun getRankRestaurant(sort : String, campus : String, size : Int) : Response<RankRestaurantResponse>

    suspend fun getOneRestaurant(storeId : Int) : Response<ResponseOneRestaurant>

    //DataStore
    suspend fun saveLunchEvaluation(type: String, evaluation: String)

    suspend fun saveSortType(key: Preferences.Key<String>, value: String)

    suspend fun saveWidgetType(type : String)

    suspend fun defaultDataStore()

    suspend fun getLunchEvaluation(): Flow<String>

    suspend fun getLunchBEvaluation(): Flow<String>

    suspend fun getDinnerEvaluation(): Flow<String>

    suspend fun getLunchSEvaluation(): Flow<String>

    suspend fun getDinnerSEvaluation(): Flow<String>

    suspend fun getLunchHEvaluation(): Flow<String>

    suspend fun getDinnerHEvaluation(): Flow<String>

    suspend fun getCurrentSortType(): Flow<String>

    suspend fun getCurrentWidgetType(): Flow<String?>

    //Room
    suspend fun deleteFoods(restaurant: Restaurant)

    //Room PagingData
    fun getFoods(): Flow<PagingData<Restaurant>>

    fun loveIs(id: String): Restaurant

    fun updateLove(id: String): Boolean

    fun getLoveIsFood() : Flow<List<Restaurant>>
}