package com.myongsik.myongsikandroid.data.model.food

/*
주간 음식 조회 Response
 */
data class WeekFoodAreaResponse(
    val success : Boolean,
    val message : String,
    val localDateTime : String,
    val httpStatus : String,
    val httpCode : Int,
    val meals : List<String>
)
