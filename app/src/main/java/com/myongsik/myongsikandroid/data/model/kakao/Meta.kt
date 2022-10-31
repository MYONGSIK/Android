package com.myongsik.myongsikandroid.data.model.kakao


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @field:Json(name = "is_end")
    val isEnd: Boolean,
    @field:Json(name = "pageable_count")
    val pageableCount: Int,
    @field:Json(name = "same_name")
    val sameName: SameName,
    @field:Json(name = "total_count")
    val totalCount: Int
)