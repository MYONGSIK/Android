package com.myongsik.myongsikandroid.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myongsik.myongsikandroid.data.model.food.*
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.review.RequestReviewData
import com.myongsik.myongsikandroid.data.model.review.ResponseReviewData
import com.myongsik.myongsikandroid.data.model.user.RequestUserData
import com.myongsik.myongsikandroid.data.model.user.ResponseUserData
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _weekGetFoodArea = MutableLiveData<WeekFoodResponse>()
    val weekGetFoodArea : LiveData<WeekFoodResponse>
        get() = _weekGetFoodArea

    private val _postReviewData = MutableLiveData<ResponseReviewData>()
    val postReviewData : LiveData<ResponseReviewData>
        get() = _postReviewData

    private val _postUserData = MutableLiveData<ResponseUserData>()
    val postUserData : LiveData<ResponseUserData>
        get() = _postUserData

    private val _postMealData = MutableLiveData<ResponseMealData>()
    val postMealData : LiveData<ResponseMealData>
        get() = _postMealData

    fun weekGetFoodAreaFun(s: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = foodRepository.weekGetFoodArea(s)

        if(response.code() == 200){
            _weekGetFoodArea.postValue(response.body())
        }
    }

    fun postReview(requestReviewData: RequestReviewData) = viewModelScope.launch(Dispatchers.IO) {
        val response = foodRepository.postReview(requestReviewData)

        if(response.code() == 200){
            _postReviewData.postValue(response.body())
        }
    }

    fun postUser(requestUserData: RequestUserData) = viewModelScope.launch(Dispatchers.IO) {
        val response = foodRepository.postUser(requestUserData)

        if(response.code() == 200){
            _postUserData.postValue(response.body())
        }
    }

    //DataStore
    fun saveLunchEvaluation(type: String, value : String) = viewModelScope.launch(Dispatchers.IO) {
        foodRepository.saveLunchEvaluation(type, value)
    }

    //DataStore 초기화
    suspend fun defaultDataStore(){
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

    //Room
    fun saveFoods(restaurant: Restaurant) = viewModelScope.launch(Dispatchers.IO) {
        foodRepository.insertFoods(restaurant)
    }

    fun deleteFoods(restaurant: Restaurant) = viewModelScope.launch(Dispatchers.IO) {
        foodRepository.deleteFoods(restaurant)
    }

    private val _loveIs = MutableLiveData<Restaurant>()
    val loveIs : LiveData<Restaurant>
        get() = _loveIs
    fun loveIs(restaurant: Restaurant) = viewModelScope.launch(Dispatchers.IO){
        val restaurantLove = foodRepository.loveIs(restaurant.id)
        _loveIs.postValue(restaurantLove)
    }

    val loveFoods : StateFlow<PagingData<Restaurant>> =
        foodRepository.getFoods()
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    private val _isUpdate = MutableLiveData<Boolean>(false)
    val isUpdate: LiveData<Boolean>
        get() = _isUpdate

    fun loveUpdate(string: String)= viewModelScope.launch(Dispatchers.IO){
        val restaurantLove = foodRepository.updateLove(string)
        _isUpdate.postValue(restaurantLove)
    }

    val loveIsFood : StateFlow<List<Restaurant>> = foodRepository.getLoveIsFood()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    private val _scrapRestaurant = MutableLiveData<ResponseScrap>()
    val scrapRestaurant : LiveData<ResponseScrap>
        get() = _scrapRestaurant
    fun scarpRestaurant(requestScrap: RequestScrap) = viewModelScope.launch(Dispatchers.IO){
        val response = foodRepository.postScrapRestaurant(requestScrap)

        if(response.code() == 200){
            _scrapRestaurant.postValue(response.body())
        }
    }

    private val _rankRestaurantResponse = MutableLiveData<RankRestaurantResponse>()
    val rankRestaurantResponse : LiveData<RankRestaurantResponse>
        get() = _rankRestaurantResponse

    fun getRankRestaurant() = viewModelScope.launch(Dispatchers.IO) {
        when (MyongsikApplication.prefs.getUserCampus()){
            "S" -> start("scrapCount,desc", "SEOUL")
            "Y" -> start("scrapCount,desc","YONGIN")
        }
    }

    private suspend fun start(sort : String, campus: String) {
        val response = foodRepository.getRankRestaurant(sort, campus)

        if(response.isSuccessful) {
            response.body()?.let { body ->
                _rankRestaurantResponse.postValue(body)
            }
        }
    }
}