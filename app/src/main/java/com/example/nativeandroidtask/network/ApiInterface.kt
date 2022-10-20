package com.example.nativeandroidtask.network


import com.example.nativeandroidtask.models.Todos
import com.example.nativeandroidtask.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface ApiInterface {
    @GET("users")
    suspend fun getUsers(
    ): Response<List<User>>

    @GET("todos")
    suspend fun getTodosByUser(
        @Query("userId") userId: Int,
        @Query("_page") page: Int,
        @Query("_limit") _limit: Int
    ): Response<List<Todos>>

    @GET("todos")
    suspend fun getTodos(
        @Query("_page") page: Int,
        @Query("_limit") _limit: Int
    ): Response<List<Todos>>
}



