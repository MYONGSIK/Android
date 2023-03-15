package com.myongsik.myongsikandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SearchViewModelProviderFactory @Inject constructor(
    private val searchFoodRepository: SearchFoodRepository
    ): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchFoodRepository) as T
        }
        throw IllegalArgumentException("viewmodel name is error")
    }
}