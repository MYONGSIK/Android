package com.myongsik.myongsikandroid.presentation.viewmodel.food

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myongsik.myongsikandroid.base.BaseResult
import com.myongsik.myongsikandroid.base.BaseViewModel
import com.myongsik.myongsikandroid.data.model.food.RankRestaurantResponse
import com.myongsik.myongsikandroid.data.model.food.ResponseMealData
import com.myongsik.myongsikandroid.data.model.food.ResponseOneRestaurant
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.data.model.review.toRequestReviewEntity
import com.myongsik.myongsikandroid.data.model.review.toResponseReviewData
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.domain.usecase.food.GetWeekFoodDataUseCase
import com.myongsik.myongsikandroid.domain.usecase.food.PostReviewDataUseCase
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val getWeekFoodDataUseCase: GetWeekFoodDataUseCase,
    private val postReviewDataUseCase: PostReviewDataUseCase
) : BaseViewModel() {

    private val _weekGetFoodArea = MutableStateFlow<WeekFoodState>(WeekFoodState.UnInitialized)
    val weekGetFoodArea: StateFlow<WeekFoodState> = _weekGetFoodArea.asStateFlow()

    private val _postReviewData = MutableStateFlow<ResponseReviewData?>(null)
    val postReviewData: StateFlow<ResponseReviewData?> = _postReviewData.asStateFlow()

    private val _postMealData = MutableLiveData<ResponseMealData>()
    val postMealData: LiveData<ResponseMealData>
        get() = _postMealData

    fun weekGetFoodAreaFun(s: String) = launch {
        _weekGetFoodArea.emit(WeekFoodState.Loading)
        getWeekFoodDataUseCase(s).collect {
            when (it) {
                is BaseResult.Success -> {
                    _weekGetFoodArea.emit(WeekFoodState.SuccessWeekFoodGetData(it.data))
                }
                is BaseResult.Error -> {
                    _weekGetFoodArea.emit(WeekFoodState.Error(it.errorCode))
                }
            }
        }
    }

    fun postReview(requestReviewData: RequestReviewData) = launch {
        postReviewDataUseCase(requestReviewData.toRequestReviewEntity())?.let{
            _postReviewData.value = it.toResponseReviewData()
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