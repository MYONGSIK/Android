package com.myongsik.myongsikandroid.data.model

data class WeekFoodResponse(
    val success : Boolean,
    val message : String,
    val localDateTime : String,
    val httpStatus : String,
    val httpCode : Int,
    val data : List<WeekFoodResult>,
)
