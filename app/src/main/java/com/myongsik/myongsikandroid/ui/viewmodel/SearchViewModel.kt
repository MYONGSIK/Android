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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchFoodRepository: SearchFoodRepository
) : ViewModel() {

    //검색화면
//    private val _resultSearch = MutableLiveData<SearchResponse>()
//    val resultSearch : LiveData<SearchResponse>
//        get() = _resultSearch
//
//    fun searchFood(query : String) = viewModelScope.launch(Dispatchers.IO) {
//        val response = searchFoodRepository.searchFood(
//            "서울 명지대 $query", "FD6, CE7", "126.923460283882",
//            "37.5803504797164", 1500, 1, 15)
//
//        if(response.isSuccessful){
//            response.body()?.let{ body ->
//                _resultSearch.postValue(body)
//            }
//        }
//    }

    //검색 페이징 -> 검색할 때, 해시태그 클릭했을 때 사용됨
    private val _searchPagingResult = MutableStateFlow<PagingData<Restaurant>>(PagingData.empty())
    val searchPagingResult : StateFlow<PagingData<Restaurant>> = _searchPagingResult.asStateFlow()

    fun searchPagingFood(query : String) {
        viewModelScope.launch{
            searchFoodRepository.searchPagingFood(query)
                .cachedIn(viewModelScope)
                .collect{
                    _searchPagingResult.value = it
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
            "37.5803504797164", 10000, 1, 10, "distance")

        if(response.isSuccessful){
            response.body()?.let{ body ->
                _resultRecommendSearch.postValue(body)
            }
        }
    }

}