package com.myongsik.myongsikandroid.data.model.food

data class RankRestaurantResponse(
    val data : Content
) {
    data class Content(
        val content : List<GetRankRestaurant>
    )
}

data class GetRankRestaurant(
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
