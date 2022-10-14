package com.myongsik.myongsikandroid.data.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
//플러그인 JSON to Kotlin -> 자동으로 만들어줌
@JsonClass(generateAdapter = true)
@Entity(tableName = "books")
data class Book(
    val authors: List<String>, //DB에 저장하기 위해 String으로 변경
    val contents: String,
    val datetime: String,
    @PrimaryKey(autoGenerate = false)
    val isbn: String, //책의 고유한 값 -> PrimaryKey
    val price: Int,
    val publisher: String,
    @ColumnInfo(name = "sale_price") //Db의 키값을 스네이크로변환
    @field:Json(name = "sale_price")
    val salePrice: Int,
    val status: String,
    val thumbnail: String,
    val title: String,
    val translators: List<String>,
    val url: String
) : Parcelable