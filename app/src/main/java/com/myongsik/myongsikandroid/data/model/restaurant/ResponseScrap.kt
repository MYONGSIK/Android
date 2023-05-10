package com.myongsik.myongsikandroid.data.model.restaurant

import com.myongsik.myongsikandroid.domain.model.restaurant.ResponseScrapEntity

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

fun ResponseScrap.toResponseScrapEntity() = ResponseScrapEntity(
    httpCode = this.httpCode,
    httpStatus = this.httpStatus,
    localDateTime = this.localDateTime,
    message = this.message,
    success = this.success,
    id = this.data.id,
    address = this.data.address,
    category = this.data.category,
    code = this.data.code,
    contact = this.data.contact,
    distance = this.data.distance,
    name = this.data.name,
    urlAddress = this.data.urlAddress
)

fun ResponseScrapEntity.toResponseScrapData() = ResponseScrap(
    httpCode = this.httpCode,
    httpStatus = this.httpStatus,
    localDateTime = this.localDateTime,
    message = this.message,
    success = this.success,
    data = ResponseScrap.ResponseScrapData(
        id = this.id,
        address = this.address,
        category = this.category,
        code = this.code,
        contact = this.contact,
        distance = this.distance,
        name = this.name,
        urlAddress = this.urlAddress
    )
)