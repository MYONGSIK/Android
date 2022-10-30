package com.myongsik.myongsikandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myongsik.myongsikandroid.data.api.SearchFoodApi
import com.myongsik.myongsikandroid.data.repository.FoodRepository
import com.myongsik.myongsikandroid.data.repository.SearchFoodRepository
import com.myongsik.myongsikandroid.data.repository.SearchFoodRepositoryImpl

@Suppress("UNCHECKED_CAST")
class SearchViewModelProviderFactory: ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            val repository = SearchFoodRepositoryImpl(SearchFoodApi.create())

            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("viewmodel name is error")
    }
}