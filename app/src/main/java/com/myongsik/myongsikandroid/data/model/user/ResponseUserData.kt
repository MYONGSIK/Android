package com.myongsik.myongsikandroid.data.model.user

data class ResponseUserData(
    val httpCode: Int,
    val success : Boolean,
    val localDataTime : ArrayList<Int>,
    val httpStatus : String,
    val message : String,
    val data : Result
){
    data class Result(
        val id: String,
        val phoneId : String
    )
}

