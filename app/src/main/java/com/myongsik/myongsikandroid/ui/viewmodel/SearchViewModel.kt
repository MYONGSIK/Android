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
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchFoodRepository: SearchFoodRepository
) : ViewModel() {

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

    private val _resultRecommendSearch = MutableLiveData<SearchResponse>()
    val resultRecommendSearch : LiveData<SearchResponse>
        get() = _resultRecommendSearch

    fun searchRecommendFood(query : String) = viewModelScope.launch(Dispatchers.IO) {
        when (MyongsikApplication.prefs.getUserCampus()){
            "S" -> start("서울", query, 126.923460283882, 37.5803504797164)
            "Y" -> start("용인", query, 127.18758354347, 37.224650469991)
        }
    }

    private suspend fun start(locate: String, query: String, x:Double, y:Double) {
        val response = searchFoodRepository.searchFood(
            "$locate 명지대 $query", "FD6, CE7", "$x",
            "$y", 10000, 1, 15, "distance")

        if(response.isSuccessful){
            response.body()?.let{ body ->
                _resultRecommendSearch.postValue(body)
            }
        }
    }
}