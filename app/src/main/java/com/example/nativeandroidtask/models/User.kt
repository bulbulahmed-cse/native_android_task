package com.example.nativeandroidtask.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val address: Address?,
    val company: Company?,
    val email: String?,
    val id: Int?,
    val name: String?,
    val phone: String?,
    val username: String?,
    val website: String?
) {
    @JsonClass(generateAdapter = true)
    data class Address(
        val city: String?,
        val geo: Geo?,
        val street: String?,
        val suite: String?,
        val zipcode: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Geo(
            val lat: String?,
            val lng: String?
        )
    }

    @JsonClass(generateAdapter = true)
    data class Company(
        val bs: String?,
        val catchPhrase: String?,
        val name: String?
    )
}