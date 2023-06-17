package com.myongsik.presentation.adapter.search

import com.myongsik.data.model.kakao.Restaurant

interface OnSearchViewHolderClick {
    fun addItem(restaurant : Restaurant)
    fun deleteItem(restaurant : Restaurant)
    fun isItem(string: String)
    fun clickDirectButton(restaurant : Restaurant)
}