package com.myongsik.data.model.restaurant

import com.myongsik.myongsikandroid.domain.model.restaurant.RequestScrapEntity

data class RequestScrap(
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

fun RequestScrap.toRequestScrapEntity() = RequestScrapEntity(
    address = this.address,
    campus = this.campus,
    category = this.category,
    code = this.code,
    contact = this.contact,
    distance = this.distance,
    name = this.name,
    phoneId = this.phoneId,
    urlAddress = this.urlAddress,
    latitude = this.latitude,
    longitude = this.longitude
)

fun RequestScrapEntity.toRequestScrapData() = RequestScrap(
    address = this.address,
    campus = this.campus,
    category = this.category,
    code = this.code,
    contact = this.contact,
    distance = this.distance,
    name = this.name,
    phoneId = this.phoneId,
    urlAddress = this.urlAddress,
    latitude = this.latitude,
    longitude = this.longitude
)
