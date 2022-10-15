package com.myongsik.myongsikandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
