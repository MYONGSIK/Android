package com.myongsik.myongsikandroid.ui.adapter.food

import com.myongsik.myongsikandroid.data.model.food.GetRankRestaurant

interface OnScrapViewHolderClick {
    fun clickRankDirectButton(getRankRestaurant: GetRankRestaurant)

    fun onHashtagGoodFoodClick()

    fun onHashtagGoodCafeClick()

    fun onHashtagGoodDrinkClick()

    fun onHashtagGoodBreadClick()

    fun onSelectSortMenu(sort: String)

    fun onClickMap()
}