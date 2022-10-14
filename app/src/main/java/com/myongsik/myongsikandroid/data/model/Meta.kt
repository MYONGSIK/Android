package com.myongsik.myongsikandroid.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
//플러그인 JSON to Kotlin -> 자동으로 만들어줌
@JsonClass(generateAdapter = true)
data class Meta(
    //Json 만으로는 변환실패하기 때문이 field 를 붙혀줌
    @field:Json(name = "is_end")
    val isEnd: Boolean,
    @field:Json(name = "pageable_count")
    val pageableCount: Int,
    @field:Json(name = "total_count")
    val totalCount: Int
)