package com.myongsik.myongsikandroid.domain.model.restaurant

data class ResponseScrapEntity(
    val httpCode : Int,
    val httpStatus : String,
    val localDateTime : String,
    val message : String,
    val success : Boolean,
    val id: Int,
    val address : String,
    val category : String,
    val code : String,
    val contact : String,
    val distance : String,
    val name : String,
    val urlAddress : String,
)