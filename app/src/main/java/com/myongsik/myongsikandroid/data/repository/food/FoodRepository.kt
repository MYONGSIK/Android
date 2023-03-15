package com.myongsik.myongsikandroid.data.repository.food

import androidx.paging.PagingData
import com.myongsik.myongsikandroid.data.model.food.FoodResult
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.data.model.user.ResponseUserData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FoodRepository {

    suspend fun weekGetFoodArea(s:String) : Response<WeekFoodResponse>

    suspend fun postReview(requestReviewData: RequestReviewData) : Response<ResponseReviewData>

    suspend fun postUser(requestUserData: RequestUserData) : Response<ResponseUserData>

    //DataStore
    suspend fun saveLunchEvaluation(type: String, evaluation : String)

    suspend fun defaultDataStore()

    suspend fun getLunchEvaluation() : Flow<String>

    suspend fun getLunchBEvaluation() : Flow<String>

    suspend fun getDinnerEvaluation() : Flow<String>

    suspend fun getLunchSEvaluation() : Flow<String>

    suspend fun getDinnerSEvaluation() : Flow<String>

    suspend fun getLunchHEvaluation() : Flow<String>

    suspend fun getDinnerHEvaluation() : Flow<String>

    //Room
    suspend fun insertFoods(restaurant: Restaurant)

    suspend fun deleteFoods(restaurant: Restaurant)

    //Room PagingData
    fun getFoods() : Flow<PagingData<Restaurant>>

    fun loveIs(id : String) : Restaurant

    fun updateLove(id : String) : Boolean
}