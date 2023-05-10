package com.myongsik.myongsikandroid.domain.model.restaurant

data class RequestScrapEntity(
    val address : String,
    val campus : String,
    val category : String,
    val code : String,
    val contact : String,
    val distance : String,
    val name : String,
    val phoneId : String,
    val urlAddress : String,
    val latitude : String,
    val longitude : String
)
