package com.myongsik.myongsikandroid.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myongsik.myongsikandroid.data.model.food.FoodResult
import com.myongsik.myongsikandroid.data.model.food.TodayFoodResponse
import com.myongsik.myongsikandroid.data.model.food.WeekFoodResponse
import com.myongsik.myongsikandroid.data.model.kakao.SearchResponse
import com.myongsik.myongsikandroid.data.repository.FoodRepository
import com.myongsik.myongsikandroid.data.repository.SearchFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class SearchViewModel(
    private val searchFoodRepository: SearchFoodRepository
) : ViewModel() {


    private val _resultSearch = MutableLiveData<SearchResponse>()
    val resultSearch : LiveData<SearchResponse>
        get() = _resultSearch

    fun searchFood(query : String) = viewModelScope.launch(Dispatchers.IO) {
        val response = searchFoodRepository.searchFood(
            "서울 명지대 $query", "FD6, CE7", "126.923460283882",
            "37.5803504797164", 10000, 1, 15)

        if(response.isSuccessful){
            response.body()?.let{ body ->
                _resultSearch.postValue(body)
            }
        }
    }

    private val _resultRecommendSearch = MutableLiveData<SearchResponse>()
    val resultRecommendSearch : LiveData<SearchResponse>
        get() = _resultRecommendSearch

    fun searchRecommendFood(query : String) = viewModelScope.launch(Dispatchers.IO) {
        val response = searchFoodRepository.searchFood(
            "서울 명지대 $query", "FD6, CE7", "126.923460283882",
            "37.5803504797164", 10000, 1, 10)

        if(response.isSuccessful){
            response.body()?.let{ body ->
                _resultRecommendSearch.postValue(body)
            }
        }
    }
}