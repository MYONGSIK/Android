package com.myongsik.data.model.kakao


import com.myongsik.data.model.food.GetRankRestaurant
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val documents: List<Restaurant>,
    val meta: Meta
)

fun SearchResponse.toRankRestaurant(): ArrayList<GetRankRestaurant> {
    val list = arrayListOf<GetRankRestaurant>()
    documents.forEach {
        list.add(
            GetRankRestaurant(
                storeId = it.id.toInt(),
                code = it.id,
                name = it.place_name,
                category = it.category_group_name,
                address = it.road_address_name,
                urlAddress = it.place_url,
                distance = it.distance,
                scrapCount = null,
                contact = it.phone,
                latitude = it.x,
                longitude = it.y
            )
        )
    }
    return list
}