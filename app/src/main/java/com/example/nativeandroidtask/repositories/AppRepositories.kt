package com.example.nativeandroidtask.repositories

import android.content.Context
import com.example.nativeandroidtask.models.Todos
import com.example.nativeandroidtask.models.User
import com.example.nativeandroidtask.network.ApiInterface
import com.example.nativeandroidtask.network.SafeApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val context: Context,
    private val api: ApiInterface,
) {
    suspend fun getUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            SafeApiRequest.apiRequest(context) {
                api.getUsers()
            }
        }
    }

    suspend fun getTodosByUser(user_id:Int?,page:Int?): List<Todos> {
        return withContext(Dispatchers.IO) {
            SafeApiRequest.apiRequest(context) {
                api.getTodosByUser(user_id!!,page!!,10)
            }
        }
    }

    suspend fun getTodos(page:Int?): List<Todos> {
        return withContext(Dispatchers.IO) {
            SafeApiRequest.apiRequest(context) {
                api.getTodos(page!!,10)
            }
        }
    }
}