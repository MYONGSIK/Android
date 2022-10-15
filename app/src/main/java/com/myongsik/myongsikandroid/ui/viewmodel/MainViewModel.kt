package com.myongsik.myongsikandroid.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myongsik.myongsikandroid.data.model.TodayFoodResponse
import com.myongsik.myongsikandroid.data.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _todayGetFood = MutableLiveData<TodayFoodResponse>()
    val todayGetFood : LiveData<TodayFoodResponse>
        get() = _todayGetFood

    private val _weekGetFood = MutableLiveData<TodayFoodResponse>()
    val weekGetFood : LiveData<TodayFoodResponse>
        get() = _weekGetFood

    fun todayGetFoodFun() = viewModelScope.launch(Dispatchers.IO){
        val response = foodRepository.todayGetFood()

        if(response.code() == 200) {
            _todayGetFood.postValue(response.body())
        }else{
            _todayGetFood.postValue(response.body())
        }
    }

    fun weekGetFoodFun() = viewModelScope.launch {
        val response = foodRepository.weekGetFood()

        if(response.code() == 200){
            _weekGetFood.postValue(response.body())
        }
    }

}