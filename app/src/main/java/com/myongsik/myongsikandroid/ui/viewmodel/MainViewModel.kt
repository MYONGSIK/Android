package com.myongsik.myongsikandroid.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myongsik.myongsikandroid.data.model.SearchResponse
import com.myongsik.myongsikandroid.data.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _resultSearch = MutableLiveData<SearchResponse>()
    val resultSearch : LiveData<SearchResponse>
        get() = _resultSearch

    fun searchBooks(query : String) = viewModelScope.launch(Dispatchers.IO){
        val response = foodRepository.searchBooks(query, "accuracy", 1, 15)
        //paging 으로 변환
        if(response.isSuccessful){
            response.body()?.let{ body ->
                _resultSearch.postValue(body)
            }
        }
    }
}