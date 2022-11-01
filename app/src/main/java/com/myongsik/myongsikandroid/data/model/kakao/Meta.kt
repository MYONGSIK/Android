package com.myongsik.myongsikandroid.data.model.kakao


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @field:Json(name = "is_end")
    val is_end: Boolean,
    @field:Json(name = "pageable_count")
    val pageable_count: Int,
    @field:Json(name = "same_name")
    val same_name: SameName,
    @field:Json(name = "total_count")
    val total_count: Int
)