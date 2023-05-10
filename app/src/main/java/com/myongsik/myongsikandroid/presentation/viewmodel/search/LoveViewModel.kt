package com.myongsik.myongsikandroid.presentation.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.myongsik.myongsikandroid.BaseViewModel
import com.myongsik.myongsikandroid.data.model.restaurant.RequestScrap
import com.myongsik.myongsikandroid.data.model.restaurant.ResponseScrap
import com.myongsik.myongsikandroid.data.model.kakao.Restaurant
import com.myongsik.myongsikandroid.data.model.kakao.toRestaurantData
import com.myongsik.myongsikandroid.data.model.kakao.toRestaurantEntity
import com.myongsik.myongsikandroid.data.model.restaurant.toRequestScrapEntity
import com.myongsik.myongsikandroid.data.model.restaurant.toResponseScrapData
import com.myongsik.myongsikandroid.data.repository.food.FoodRepository
import com.myongsik.myongsikandroid.domain.usecase.restaurant.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LoveViewModel @Inject constructor(
    private val insertRestaurantDataUseCase: InsertRestaurantDataUseCase,
    private val deleteRestaurantDataUseCase: DeleteRestaurantDataUseCase,
    private val loveIsRestaurantDataUseCase: LoveIsRestaurantDataUseCase,
    private val getListRestaurantDataUseCase: GetListRestaurantDataUseCase,
    private val getPagingRestaurantDataUseCase: GetPagingRestaurantDataUseCase,
    private val postScrapRestaurantDataUseCase: PostScrapRestaurantDataUseCase
) : BaseViewModel() {

    //Room
    fun saveFoods(restaurant: Restaurant) = launch {
        insertRestaurantDataUseCase(restaurant.toRestaurantEntity())
    }

    fun deleteFoods(restaurant: Restaurant) = launch {
        deleteRestaurantDataUseCase(restaurant.toRestaurantEntity())
    }

    private val _loveIs = MutableStateFlow<Restaurant?>(null)
    val loveIs: StateFlow<Restaurant?> = _loveIs.asStateFlow()

    fun loveIs(restaurant: Restaurant) = launch {
        loveIsRestaurantDataUseCase(restaurant.id).let{
            _loveIs.value = it.toRestaurantData()
        }
    }

    private val _loveListRestaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val loveListRestaurants : StateFlow<List<Restaurant>> = _loveListRestaurants.asStateFlow()

    fun getLoveListRestaurant() = launch {
        getListRestaurantDataUseCase()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
            .collect { restaurantList ->
                _loveListRestaurants.value = restaurantList.map { it.toRestaurantData()}
            }
    }

    private val _loveRestaurants = MutableStateFlow<PagingData<Restaurant>>(PagingData.empty())
    val loveRestaurants : StateFlow<PagingData<Restaurant>> = _loveRestaurants.asStateFlow()

    fun getLoveRestaurant() = launch {
        getPagingRestaurantDataUseCase()
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
            .collect { restaurantPagingData ->
                _loveRestaurants.value = restaurantPagingData.map { it.toRestaurantData() }
            }
    }

    private val _scrapRestaurant = MutableLiveData<ResponseScrap>()
    val scrapRestaurant: LiveData<ResponseScrap>
        get() = _scrapRestaurant

    fun scarpRestaurant(requestScrap: RequestScrap) = launch {
        postScrapRestaurantDataUseCase(requestScrap.toRequestScrapEntity())?.let{
            _scrapRestaurant.postValue(it.toResponseScrapData())
        }
    }
}