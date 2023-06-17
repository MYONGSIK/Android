package com.myongsik.data.model.food

data class ResponseOneRestaurant(
    val httpCode : Int,
    val httpStatus : String,
    val localDateTime : String,
    val message : String,
    val success : Boolean,
    val data : ResponseOneRestaurantData
) {
    data class ResponseOneRestaurantData(
        val storeId : Int,
        val code : String,
        val name : String,
        val category : String,
        val address : String,
        val urlAddress : String,
        val distance : String,
        val scrapCount : Int?,
        val contact : String,
        val latitude : String?,
        val longitude : String?
    )
}
