package com.myongsik.presentation.adapter.food

import com.myongsik.data.model.food.GetRankRestaurant

interface OnScrapViewHolderClick {
    fun clickRankDirectButton(getRankRestaurant: GetRankRestaurant)

    fun onHashtagGoodFoodClick()

    fun onHashtagGoodCafeClick()

    fun onHashtagGoodDrinkClick()

    fun onHashtagGoodBreadClick()

    fun onSelectSortMenu(sort: String)
}