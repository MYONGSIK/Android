package com.myongsik.myongsikandroid.presentation.viewmodel.food

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myongsik.myongsikandroid.base.ApiResponse
import com.myongsik.myongsikandroid.base.BaseViewModel
import com.myongsik.myongsikandroid.data.model.food.RankRestaurantResponse
import com.myongsik.myongsikandroid.data.model.food.ResponseMealData
import com.myongsik.myongsikandroid.data.model.food.ResponseOneRestaurant
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    private val _weekGetFoodArea = MutableLiveData<ApiResponse<WeekFoodResponse>>()
    val weekGetFoodArea: LiveData<ApiResponse<WeekFoodResponse>>
        get() = _weekGetFoodArea

    private val _postReviewData = MutableLiveData<ResponseReviewData>()
    val postReviewData: LiveData<ResponseReviewData>
        get() = _postReviewData

    private val _postMealData = MutableLiveData<ResponseMealData>()
    val postMealData: LiveData<ResponseMealData>
        get() = _postMealData

    fun weekGetFoodAreaFun(s: String) = launch {
        foodRepository.weekGetFoodArea(s).collect{
            _weekGetFoodArea.postValue(it)
        }
    }

    fun postReview(requestReviewData: RequestReviewData) = launch {
        val response = foodRepository.postReview(requestReviewData)

        if (response.code() == 200) {
            _postReviewData.postValue(response.body())
        }
    }

    //DataStore
    fun saveSortType(key: Preferences.Key<String>, value: String) = launch {
        foodRepository.saveSortType(key, value)
    }

    suspend fun getCurrentSortType() = withContext(Dispatchers.IO) {
        foodRepository.getCurrentSortType().first()
    }

    private val _rankRestaurantResponse = MutableLiveData<RankRestaurantResponse>()
    val rankRestaurantResponse: LiveData<RankRestaurantResponse>
        get() = _rankRestaurantResponse

    fun getRankRestaurant() = launch {
        when (MyongsikApplication.prefs.getUserCampus()) {
            Constant.S -> start("${Constant.SCRAP_COUNT},${Constant.DESC}", Constant.SEOUL, 20)
            Constant.Y -> start("${Constant.SCRAP_COUNT},${Constant.DESC}", Constant.YONGIN, 20)
        }
    }

    fun getDistanceRestaurant() = launch {
        when (MyongsikApplication.prefs.getUserCampus()) {
            Constant.S -> start("${Constant.DISTANCE},${Constant.ASC}", Constant.SEOUL, 20)
            Constant.Y -> start("${Constant.DISTANCE},${Constant.ASC}", Constant.YONGIN, 20)
        }
    }

    fun getMapRankRestaurant() = launch {
        when (MyongsikApplication.prefs.getUserCampus()) {
            Constant.S -> start("${Constant.SCRAP_COUNT},${Constant.ASC}", Constant.SEOUL, Int.MAX_VALUE)
            Constant.Y -> start("${Constant.SCRAP_COUNT},${Constant.ASC}", Constant.YONGIN, Int.MAX_VALUE)
        }
    }

    private suspend fun start(sort: String, campus: String, size : Int) {
        val response = foodRepository.getRankRestaurant(sort, campus, size)

        if (response.isSuccessful) {
            response.body()?.let { body ->
                _rankRestaurantResponse.postValue(body)
            }
        }
    }

    private val _getDetailRestaurant = MutableLiveData<ResponseOneRestaurant>()
    val getDetailRestaurant: LiveData<ResponseOneRestaurant>
        get() = _getDetailRestaurant

    fun getOneRestaurant(storeId : Int) = launch {
        val response = foodRepository.getOneRestaurant(storeId)

        if (response.isSuccessful) {
            _getDetailRestaurant.postValue(response.body())
        }
    }
}