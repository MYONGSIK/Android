package com.myongsik.myongsikandroid.data.model.food

/*
오늘 음식 조회 Response
 */
data class TodayFoodResponse(
    val success : Boolean,
    val message : String,
    val localDateTime : String,
    val httpStatus : String,
    val httpCode : Int,
    val data : List<FoodResult>,
//    에러 상황
    val timeStamp : String,
    val dayOfTheWeek : String,
    val errorCode : String,
)
