package com.myongsik.myongsikandroid.presentation.adapter.search

import com.myongsik.myongsikandroid.data.model.kakao.Restaurant

interface OnSearchViewHolderClick {
    fun addItem(restaurant : Restaurant)
    fun deleteItem(restaurant : Restaurant)
    fun isItem(string: String)
    fun clickDirectButton(restaurant : Restaurant)
}