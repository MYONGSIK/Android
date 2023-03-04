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

/*
Repository 패턴을 이용한 FoodRepository interface
 */
interface FoodRepository {

    //오늘 식단, 주간식단 불러오기

    suspend fun weekGetFoodArea(s:String) : Response<WeekFoodResponse>

    suspend fun postReview(requestReviewData: RequestReviewData) : Response<ResponseReviewData>

    suspend fun postUser(requestUserData: RequestUserData) : Response<ResponseUserData>

    //DataStore
    //DataStore 저장
    suspend fun saveLunchEvaluation(type: String, evaluation : String)

    //하루가 지나면 DataStore 초기화
    suspend fun defaultDataStore()

    //중식평가 DataStore 불러오기
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

    //식당 조회 -> PagingData
    fun getFoods() : Flow<PagingData<Restaurant>>

    //들어간 장소가 현재 찜해뒀는지 판단하는 메서드
    fun loveIs(id : String) : Restaurant

    fun updateLove(id : String) : Boolean
}