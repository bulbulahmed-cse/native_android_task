package com.example.nativeandroidtask.network


import com.example.nativeandroidtask.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface ApiInterface {
    @GET("users")
    suspend fun getUsers(

    ): Response<List<User>>

}



