package com.example.nativeandroidtask.views.fragments.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nativeandroidtask.models.Todos
import com.example.nativeandroidtask.models.User
import com.example.nativeandroidtask.network.ApiException
import com.example.nativeandroidtask.repositories.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodosViewModel  @Inject constructor(private val repository: AppRepository) : ViewModel() {

    private val _users: MutableLiveData<
            List<User>> by lazy {
        MutableLiveData<
                List<User>>()
    }

    val users: LiveData<
            List<User>?>
        get() = _users

    //----------------------------------------------//
    private val _todosList: MutableLiveData<
            List<Todos>> by lazy {
        MutableLiveData<
                List<Todos>>()
    }

    val todosList: LiveData<
            List<Todos>?>
        get() = _todosList

    //----------------------------------------------//

    private val _eventShowMessage: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    val eventShowMessage: LiveData<String?>
        get() = _eventShowMessage

    //----------------------------------------------//

    private val _eventShowLoading: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>()
    }

    val eventShowLoading: LiveData<Boolean?>
        get() = _eventShowLoading

    //----------------------------------------------//


    private val _loadMoreLoading: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>()
    }

    val loadMoreLoading: LiveData<Boolean?>
        get() = _loadMoreLoading

    //----------------------------------------------//

    fun getUser() = viewModelScope.launch {

        try {

                _eventShowLoading.value = true

                val response = repository.getUsers()

                if (response.isNotEmpty()) {


                    _users.value = response

                } else {

                    throw ApiException(response.toString())

                }

        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }

        _eventShowLoading.value = false

    }

    //----------------------------------------------//

    fun getTodos(
        userId :Int?,
        page :Int,
    ) = viewModelScope.launch {
        try {
            if (page>1){
                _loadMoreLoading.value = true
            }else{
                _eventShowLoading.value = true
            }
            if (userId != null){
                val response = repository.getTodosByUser(userId,page)
                if (response.isNotEmpty()) {
                    if (page>1){
                        _todosList.value  = _todosList.value?.plus(response)
                    }else{
                        _todosList.value = response
                    }
                } else {
                    throw ApiException("No more data")
                }
            }else{
                val response = repository.getTodos(page)
                if (response.isNotEmpty()) {
                    if (page>1){
                        _todosList.value  = _todosList.value?.plus(response)
                    }else{
                        _todosList.value = response
                    }
                } else {
                    throw ApiException(response.toString())
                }
            }

        } catch (e: ApiException) {
            _eventShowMessage.value = e.message
        }
        _eventShowLoading.value = false
        _loadMoreLoading.value = false

    }

}