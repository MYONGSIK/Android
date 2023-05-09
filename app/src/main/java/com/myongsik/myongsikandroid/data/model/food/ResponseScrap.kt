package com.myongsik.myongsikandroid.data.model.food

data class ResponseScrap(
    val httpCode : Int,
    val httpStatus : String,
    val localDateTime : String,
    val message : String,
    val success : Boolean,
    val data : ResponseScrapData
) {
    data class ResponseScrapData(
        val id: Int,
        val address : String,
        val category : String,
        val code : String,
        val contact : String,
        val distance : String,
        val name : String,
        val urlAddress : String,
    )
}
