package com.myongsik.myongsikandroid.ui.viewmodel.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LoveViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : BaseViewModel() {

    fun deleteFoods(restaurant: Restaurant) = launch {
        foodRepository.deleteFoods(restaurant)
    }

    val loveIsFood: StateFlow<List<Restaurant>> = foodRepository.getLoveIsFood()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    val loveFoods: StateFlow<PagingData<Restaurant>> =
        foodRepository.getFoods()
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
}