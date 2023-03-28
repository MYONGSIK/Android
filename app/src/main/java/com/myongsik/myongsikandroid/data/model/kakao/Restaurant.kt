package com.myongsik.myongsikandroid.data.model.kakao


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myongsik.myongsikandroid.data.model.food.RankRestaurantResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName="love_list")
data class Restaurant(
    @field:Json(name = "address_name")
    val address_name: String,
    @field:Json(name = "category_group_code")
    val category_group_code: String,
    @field:Json(name = "category_group_name")
    val category_group_name: String,
    @field:Json(name = "category_name")
    val category_name: String,
    @field:Json(name = "distance")
    val distance: String,
    @field:Json(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @field:Json(name = "phone")
    val phone: String,
    @field:Json(name = "place_name")
    val place_name: String,
    @field:Json(name = "place_url")
    val place_url: String,
    @field:Json(name = "road_address_name")
    val road_address_name: String,
    @field:Json(name = "x")
    val x: String,
    @field:Json(name = "y")
    val y: String
) : Parcelable