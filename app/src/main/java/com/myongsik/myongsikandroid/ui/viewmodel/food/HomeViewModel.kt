package com.myongsik.myongsikandroid.ui.viewmodel.food

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myongsik.myongsikandroid.data.model.food.*
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.ui.viewmodel.BaseViewModel
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    private val _weekGetFoodArea = MutableLiveData<WeekFoodResponse>()
    val weekGetFoodArea: LiveData<WeekFoodResponse>
        get() = _weekGetFoodArea

    private val _postReviewData = MutableLiveData<ResponseReviewData>()
    val postReviewData: LiveData<ResponseReviewData>
        get() = _postReviewData

    private val _postMealData = MutableLiveData<ResponseMealData>()
    val postMealData: LiveData<ResponseMealData>
        get() = _postMealData

    fun weekGetFoodAreaFun(s: String) = launch {
        val response = foodRepository.weekGetFoodArea(s)

        if (response.code() == 200) {
            _weekGetFoodArea.postValue(response.body())
        }
    }

    fun postReview(requestReviewData: RequestReviewData) = launch {
        val response = foodRepository.postReview(requestReviewData)

        if (response.code() == 200) {
            _postReviewData.postValue(response.body())
        }
    }

    //DataStore
    fun saveLunchEvaluation(type: String, value: String) = launch {
        foodRepository.saveLunchEvaluation(type, value)
    }

    fun saveSortType(key: Preferences.Key<String>, value: String) = launch {
        foodRepository.saveSortType(key, value)
    }

    //DataStore 초기화
    suspend fun defaultDataStore() {
        foodRepository.defaultDataStore()
    }

    //중식 평가 불러오기
    suspend fun getLunchEvaluation() = withContext(Dispatchers.IO) {
        foodRepository.getLunchEvaluation().first()
    }

    suspend fun getLunchBEvaluation() = withContext(Dispatchers.IO) {
        foodRepository.getLunchBEvaluation().first()
    }

    suspend fun getDinnerEvaluation() = withContext(Dispatchers.IO) {
        foodRepository.getDinnerEvaluation().first()
    }

    suspend fun getLunchHEvaluation() = withContext(Dispatchers.IO) {
        foodRepository.getLunchHEvaluation().first()
    }

    suspend fun getDinnerHEvaluation() = withContext(Dispatchers.IO) {
        foodRepository.getDinnerHEvaluation().first()
    }

    suspend fun getLunchSEvaluation() = withContext(Dispatchers.IO) {
        foodRepository.getLunchSEvaluation().first()
    }

    suspend fun getDinnerSEvaluation() = withContext(Dispatchers.IO) {
        foodRepository.getDinnerSEvaluation().first()
    }

    suspend fun getCurrentSortType() = withContext(Dispatchers.IO) {
        foodRepository.getCurrentSortType().first()
    }

    private val _rankRestaurantResponse = MutableLiveData<RankRestaurantResponse>()
    val rankRestaurantResponse: LiveData<RankRestaurantResponse>
        get() = _rankRestaurantResponse

    fun getRankRestaurant() = launch {
        when (MyongsikApplication.prefs.getUserCampus()) {
            Constant.S -> start("${Constant.SCRAP_COUNT},${Constant.DESC}", Constant.SEOUL)
            Constant.Y -> start("${Constant.SCRAP_COUNT},${Constant.DESC}", Constant.YONGIN)
        }
    }

    fun getDistanceRestaurant() = launch {
        when (MyongsikApplication.prefs.getUserCampus()) {
            Constant.S -> start("${Constant.DISTANCE},${Constant.ASC}", Constant.SEOUL)
            Constant.Y -> start("${Constant.DISTANCE},${Constant.ASC}", Constant.YONGIN)
        }
    }

    private suspend fun start(sort: String, campus: String) {
        val response = foodRepository.getRankRestaurant(sort, campus)

        if (response.isSuccessful) {
            response.body()?.let { body ->
                _rankRestaurantResponse.postValue(body)
            }
        }
    }
}