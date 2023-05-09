package com.myongsik.myongsikandroid.presentation.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myongsik.myongsikandroid.BaseViewModel
import com.myongsik.myongsikandroid.data.model.food.RequestScrap
import com.myongsik.myongsikandroid.data.model.food.ResponseScrap
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.kakao.toRestaurantData
import com.myongsik.myongsikandroid.data.model.kakao.toRestaurantEntity
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.domain.usecase.restaurant.DeleteRestaurantDataUseCase
import com.myongsik.myongsikandroid.domain.usecase.restaurant.InsertRestaurantDataUseCase
import com.myongsik.myongsikandroid.domain.usecase.restaurant.LoveIsRestaurantDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoveViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val insertRestaurantDataUseCase: InsertRestaurantDataUseCase,
    private val deleteRestaurantDataUseCase: DeleteRestaurantDataUseCase,
    private val loveIsRestaurantDataUseCase: LoveIsRestaurantDataUseCase
) : BaseViewModel() {

    //Room
    fun saveFoods(restaurant: Restaurant) = launch {
        insertRestaurantDataUseCase(restaurant.toRestaurantEntity())
    }

    fun deleteFoods(restaurant: Restaurant) = launch {
        deleteRestaurantDataUseCase(restaurant.toRestaurantEntity())
    }

    private val _loveIs = MutableLiveData<Restaurant>()
    val loveIs: LiveData<Restaurant>
        get() = _loveIs

    fun loveIs(restaurant: Restaurant) = launch {
        loveIsRestaurantDataUseCase(restaurant.id).let{
            _loveIs.postValue(it.toRestaurantData())
        }
    }

    val loveIsFood: StateFlow<List<Restaurant>> = foodRepository.getLoveIsFood()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    val loveFoods: StateFlow<PagingData<Restaurant>> =
        foodRepository.getFoods()
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    private val _scrapRestaurant = MutableLiveData<ResponseScrap>()
    val scrapRestaurant: LiveData<ResponseScrap>
        get() = _scrapRestaurant

    fun scarpRestaurant(requestScrap: RequestScrap) = launch {
        val response = foodRepository.postScrapRestaurant(requestScrap)

        if (response.code() == 200) {
            _scrapRestaurant.postValue(response.body())
        }
    }
}