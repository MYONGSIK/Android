package com.myongsik.myongsikandroid.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myongsik.myongsikandroid.data.model.FoodResult
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.WeekFoodResponse
import com.myongsik.myongsikandroid.data.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _todayGetFood = MutableLiveData<TodayFoodResponse>()
    val todayGetFood : LiveData<TodayFoodResponse>
        get() = _todayGetFood

    private val _weekGetFood = MutableLiveData<WeekFoodResponse>()
    val weekGetFood : LiveData<WeekFoodResponse>
        get() = _weekGetFood

    //오늘 음식 가져오기
    fun todayGetFoodFun() = viewModelScope.launch(Dispatchers.IO){
        val response = foodRepository.todayGetFood()

        if(response.code() == 200) {
            _todayGetFood.postValue(response.body())
        }else{
            _todayGetFood.postValue(response.body())
        }
    }

    //이번 주 음식 가져오기
    fun weekGetFoodFun() = viewModelScope.launch(Dispatchers.IO) {
        val response = foodRepository.weekGetFood()

        if(response.code() == 200){
            _weekGetFood.postValue(response.body())
        }
    }

    //DataStore
    //중식 평가 저장
    fun saveLunchEvaluation(foodResult: FoodResult, value : String) = viewModelScope.launch(Dispatchers.IO) {
        foodRepository.saveLunchEvaluation(foodResult, value)
    }

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
}