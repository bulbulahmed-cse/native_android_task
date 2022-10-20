package com.example.nativeandroidtask.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Todos(
    @Json(name = "completed")
    val completed: Boolean?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "userId")
    val userId: Int?
)