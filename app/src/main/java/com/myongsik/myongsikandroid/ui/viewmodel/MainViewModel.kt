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


    //이번 주 음식 가져오기
    fun weekGetFoodAreaFun(s: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = foodRepository.weekGetFoodArea(s)

        if(response.code() == 200){
            _weekGetFoodArea.postValue(response.body())
        }
    }

    // 리뷰 작성하기
    fun postReview(requestReviewData: RequestReviewData) = viewModelScope.launch(Dispatchers.IO) {
        val response = foodRepository.postReview(requestReviewData)

        if(response.code() == 200){
            _postReviewData.postValue(response.body())
        }
    }

    // 회원등록
    fun postUser(requestUserData: RequestUserData) = viewModelScope.launch(Dispatchers.IO) {
        val response = foodRepository.postUser(requestUserData)

        if(response.code() == 200){
            _postUserData.postValue(response.body())
        }
    }
    //DataStore
    //중식 평가 저장
    fun saveLunchEvaluation(foodResult: FoodResult, value : String) = viewModelScope.launch(Dispatchers.IO) {
        foodRepository.saveLunchEvaluation(foodResult, value)
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

    //석식 평가 불러오기
    suspend fun getDinnerEvaluation() = withContext(Dispatchers.IO) {
        foodRepository.getDinnerEvaluation().first()
    }

    //Room
    fun saveFoods(restaurant: Restaurant) = viewModelScope.launch(Dispatchers.IO) {
        foodRepository.insertFoods(restaurant)
    }

    fun deleteFoods(restaurant: Restaurant) = viewModelScope.launch(Dispatchers.IO) {
        foodRepository.deleteFoods(restaurant)
    }

//    fun addWeekFoodItem(weekFood : WeekFoodResult) = viewModelScope.launch(Dispatchers.IO) {
//        saveLunchEvaluation(,"")
//    }

    //식당 웹뷰 들어왔을 때 이미 존재하는지 안하는지 판단하기 위한 메서드
    private val _loveIs = MutableLiveData<Restaurant>()
    val loveIs : LiveData<Restaurant>
        get() = _loveIs
    fun loveIs(restaurant: Restaurant) = viewModelScope.launch(Dispatchers.IO){
        val restaurantLove = foodRepository.loveIs(restaurant.id)
        _loveIs.postValue(restaurantLove)
    }

    //Room Paging
    //관심목록 PagingData
    val loveFoods : StateFlow<PagingData<Restaurant>> =
        foodRepository.getFoods()
            .cachedIn(viewModelScope) //코루틴이 데이터 스트림을 캐시 가능하게함
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())


    // 하트 시도 - food repository 에 id 있는지 조회하는 메소드
    private val _isUpdate = MutableLiveData<Boolean>(false)
    val isUpdate: LiveData<Boolean>
        get() = _isUpdate
    fun loveUpdate(string: String)= viewModelScope.launch(Dispatchers.IO){
        val restaurantLove = foodRepository.updateLove(string)
        _isUpdate.postValue(restaurantLove)
    }
}