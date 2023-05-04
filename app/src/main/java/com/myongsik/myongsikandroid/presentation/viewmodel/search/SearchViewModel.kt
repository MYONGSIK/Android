package com.myongsik.myongsikandroid.presentation.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.kakao.SearchResponse
import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepository
import com.myongsik.myongsikandroid.BaseViewModel
import com.myongsik.myongsikandroid.util.Constant
import com.myongsik.myongsikandroid.util.MyongsikApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchFoodRepository: SearchFoodRepository
) : BaseViewModel() {

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

    fun searchRecommendFood(query : String) = launch {
        when (MyongsikApplication.prefs.getUserCampus()){
            Constant.S -> start(Constant.SEOUL_CAMPUS, query, Constant.SEOUL_CAMPUS_X, Constant.SEOUL_CAMPUS_Y)
            Constant.Y -> start(Constant.YONGIN_CAMPUS, query, Constant.YONGIN_CAMPUS_X, Constant.YONGIN_CAMPUS_Y)
        }
    }

    private suspend fun start(locate: String, query: String, x:Double, y:Double) {
        val response = searchFoodRepository.searchFood(
            "$locate $query", Constant.CATEGORY_GROUP_CODE, "$x",
            "$y", 10000, 1, 15, Constant.DISTANCE)

        if(response.isSuccessful){
            response.body()?.let{ body ->
                _resultRecommendSearch.postValue(body)
            }
        }
    }
}