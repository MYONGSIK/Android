package com.myongsik.myongsikandroid.data.model.food

data class ResponseMealData(
    val httpCode: Int,
    val success : Boolean,
    val localDataTime : ArrayList<Int>,
    val httpStatus : String,
    val message : String,
    val data : Boolean
)


