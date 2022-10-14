package com.myongsik.myongsikandroid.data.model


import com.squareup.moshi.JsonClass
//플러그인 JSON to Kotlin -> 자동으로 만들어줌
@JsonClass(generateAdapter = true)
data class SearchResponse(
    val documents: List<Book>,
    val meta: Meta
)