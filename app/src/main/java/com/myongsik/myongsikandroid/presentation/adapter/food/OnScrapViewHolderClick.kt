package com.myongsik.myongsikandroid.presentation.adapter.food

import com.myongsik.myongsikandroid.data.model.food.GetRankRestaurant

interface OnScrapViewHolderClick {
    fun clickRankDirectButton(getRankRestaurant: GetRankRestaurant)

    fun onHashtagGoodFoodClick()

    fun onHashtagGoodCafeClick()

    fun onHashtagGoodDrinkClick()

    fun onHashtagGoodBreadClick()

    fun onSelectSortMenu(sort: String)
}