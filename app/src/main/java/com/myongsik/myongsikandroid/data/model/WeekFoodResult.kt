package com.myongsik.myongsikandroid.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

//주간 식단 조회
data class WeekFoodResult(
    val dayOfTheWeek: String,
    val lunch1: String,
    val lunch2: String,
    val lunch3: String,
    val lunch4: String,
    val lunch5: String,
    val lunch6: String,
    val toDay: String,
    val dinner1 : String,
    val dinner2 : String,
    val dinner3 : String,
    val dinner4 : String,
    val dinner5 : String,
    val dinner6 : String,
)
