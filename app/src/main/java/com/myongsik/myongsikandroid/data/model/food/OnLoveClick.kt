package com.myongsik.myongsikandroid.data.model.food

import com.myongsik.myongsikandroid.data.model.kakao.Restaurant

interface OnLoveClick {
    fun addItem(restaurant : Restaurant)
    fun deleteItem(restaurant : Restaurant)
    fun isItem(string: String)
//    fun getValue(): Int?
}