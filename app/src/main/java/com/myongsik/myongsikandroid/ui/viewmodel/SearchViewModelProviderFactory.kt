package com.myongsik.myongsikandroid.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.RoomDatabase
import com.myongsik.myongsikandroid.data.api.SearchFoodApi
import com.myongsik.myongsikandroid.data.db.RestaurantDao
import com.myongsik.myongsikandroid.data.db.RestaurantDatabase
import com.myongsik.myongsikandroid.data.repository.search.SearchFoodRepositoryImpl

@Suppress("UNCHECKED_CAST")
class SearchViewModelProviderFactory: ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            val repository = SearchFoodRepositoryImpl(SearchFoodApi.create())

            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("viewmodel name is error")
    }
}