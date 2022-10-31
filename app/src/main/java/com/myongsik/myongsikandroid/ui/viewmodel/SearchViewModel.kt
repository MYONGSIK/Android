package com.myongsik.myongsikandroid.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.kakao.SearchResponse
import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchFoodRepository: SearchFoodRepository
) : ViewModel() {

    //검색화면
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

    //추천화면
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