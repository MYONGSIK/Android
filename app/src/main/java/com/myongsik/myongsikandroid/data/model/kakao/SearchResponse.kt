package com.myongsik.myongsikandroid.data.model.kakao


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val documents: List<Restaurant>,
    val meta: Meta
)